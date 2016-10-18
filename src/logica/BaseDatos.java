package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

	private String PASS = "";
	private String USER = "SA";
	private String URL = "jdbc:hsqldb:hsql://localhost/labdb";
	private Connection conexion;

	@SuppressWarnings("serial")
	public class ExceptionUsuarioContraseña extends Exception {
		public ExceptionUsuarioContraseña(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public class ExcepcionUsuarioNoEncontrado extends Exception {
		public ExcepcionUsuarioNoEncontrado(String msg) {
			super(msg);
		}

	}

	public Connection conectar() {
		try {
			conexion = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexion;
	}

	public boolean comprobarUserPassword(int user, String password) throws Exception {
		Usuario usuario = cargarUsuario(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUsuarioContraseña("EL usuario y la contraseña no coinciden.");
	}
	
	/**
	 * Comprobar administrador y contraseña.
	 * @param user
	 * @param password
	 * @return
	 * @throws ExcepcionUsuarioNoEncontrado
	 * @throws ExceptionUsuarioContraseña
	 */
	public boolean comprobarUserPasswordAdmin(String user, String password)
			throws ExcepcionUsuarioNoEncontrado, ExceptionUsuarioContraseña {
		Usuario usuario = cargarAdministrador(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUsuarioContraseña("EL usuario y la contraseña no coinciden.");
	}
	
	public Usuario cargarAdministrador(String identificador) {
		try {
			Usuario user = null;
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from USUARIO WHERE NOMBRE = ?");
			ps.setString(1, identificador);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int user_id = rs.getInt("ID_USUARIO");
				String dni = rs.getString("DNI");
				String nombre = rs.getString("NOMBRE");
				String apellidos = rs.getString("APELLIDOS");
				String direccion = rs.getString("DIRECCION");
				String email = rs.getString("EMAIL");
				String ciudad = rs.getString("CIUDAD");
				String password = rs.getString("PASSWORD");
				double cuota = rs.getDouble("CUOTA");
				boolean baja = rs.getBoolean("BAJA");
				Timestamp fecha_baja = rs.getTimestamp("FECHA_BAJA");
				user = new Usuario(user_id, dni, nombre, apellidos, direccion, email, ciudad, password,cuota,baja,fecha_baja);
			}
			rs.close();
			ps.close();
			con.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Usuario cargarUsuario(int identificador) {
		try {
			Usuario user = null;
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from USUARIO WHERE ID_USUARIO = ?");
			ps.setInt(1, identificador);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int user_id = rs.getInt("ID_USUARIO");
				String dni = rs.getString("DNI");
				String nombre = rs.getString("NOMBRE");
				String apellidos = rs.getString("APELLIDOS");
				String direccion = rs.getString("DIRECCION");
				String email = rs.getString("EMAIL");
				String ciudad = rs.getString("CIUDAD");
				String password = rs.getString("PASSWORD");
				double cuota = rs.getDouble("CUOTA");
				boolean baja = rs.getBoolean("BAJA");
				Timestamp fecha_baja = rs.getTimestamp("FECHA_BAJA");
				user = new Usuario(user_id, dni, nombre, apellidos, direccion, email, ciudad, password,cuota,baja,fecha_baja);
			}
			rs.close();
			ps.close();
			con.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Sala> cargarDisponibilidadSalaSeleccionada(int codigo) {
		try {
			List<Sala> salas = new ArrayList<Sala>();
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from SALA WHERE ID_SALA = ?");
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();

			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM RESERVA WHERE ID_SALA = ?");
			ps1.setInt(1, codigo);
			ResultSet rs1 = ps1.executeQuery();
			List<Reserva> reservas = new ArrayList<Reserva>();
			while (rs1.next()) {
				int id = rs1.getInt("ID_RESERVA");
				Timestamp hora_inicio = rs1.getTimestamp("HORA_INICIO");
				Timestamp hora_fin = rs1.getTimestamp("HORA_FIN");
				int id_usuario = rs1.getInt("ID_USUARIO");
				int id_sala = rs1.getInt("ID_SALA");
				reservas.add(new Reserva(id, id_usuario, id_sala, hora_inicio, hora_fin, null, null));
			}
			while (rs.next()) {
				salas.add(new Sala(rs.getInt(1), rs.getString(2), rs.getDouble(3), reservas));
			}
			rs.close();
			rs1.close();
			ps.close();
			ps1.close();
			con.close();
			return salas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Sala> cargarSalas() {
		try {
			List<Sala> salas = new ArrayList<Sala>();
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from SALA");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				salas.add(new Sala(rs.getInt(1), rs.getString(2), rs.getDouble(3), null));
			}
			rs.close();
			ps.close();
			con.close();
			return salas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Devuelve verdadero si esta disponible y false en caso de que este
	 * ocupada.
	 * 
	 * @param codigoSala
	 * @param horaInicio
	 * @return
	 */
	public boolean comprobarReservaSala(String codigoSala, Timestamp horaInicio) {
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"SELECT R.ID_RESERVA FROM RESERVA r WHERE r.ID_SALA = ? AND r.HORA_INICIO = ?");
			ps.setString(1, codigoSala);
			ps.setTimestamp(2, horaInicio);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				rs.close();
				ps.close();
				con.close();
				return false;
			} else {
				rs.close();
				ps.close();
				con.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Devuelve verdadero si esta no tiene reserva simultanea y false en caso
	 * contrario.
	 * 
	 * @param id_usuario
	 * @param horaInicio
	 * @return
	 */
	public boolean comprobarReservaSimultaneaUsuario(int id_usuario, Timestamp horaInicio) {
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"SELECT R.ID_RESERVA FROM RESERVA r WHERE r.ID_USUARIO = ? AND r.HORA_INICIO = ?");
			ps.setInt(1, id_usuario);
			ps.setTimestamp(2, horaInicio);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				rs.close();
				ps.close();
				con.close();
				return false;
			} else {
				rs.close();
				ps.close();
				con.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}