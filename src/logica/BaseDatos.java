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

	public boolean comprobarUserPassword(String user, String password) throws Exception {
		Usuario usuario = cargarUsuario(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUsuarioContraseña("EL usuario y la contraseña no coinciden.");
	}

	public Usuario cargarUsuario(String identificador) {
		try {
			Usuario user = null;
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from USUARIO WHERE ID = ?");
			ps.setString(1, identificador);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String user_id = rs.getString("ID");
				String dni = rs.getString("DNI");
				String nombre = rs.getString("NOMBRE");
				String apellidos = rs.getString("APELLIDOS");
				String direccion = rs.getString("DIRECCION");
				String email = rs.getString("EMAIL");
				String ciudad = rs.getString("CIUDAD");
				String password = rs.getString("PASSWORD");
				user = new Usuario(user_id, dni, nombre, apellidos, direccion, email, ciudad, password);
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

	public List<Sala> cargarDisponibilidadSalaSeleccionada(String codigo) {
		try {
			List<Sala> salas = new ArrayList<Sala>();
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("Select * from SALA WHERE CODIGO = ?");
			ps.setString(1, codigo);
			ResultSet rs = ps.executeQuery();

			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM RESERVA WHERE CODIGO_SALA = ?");
			ps1.setString(1, codigo);
			ResultSet rs1 = ps1.executeQuery();
			List<Reserva> reservas = new ArrayList<Reserva>();
			while (rs1.next()) {
				reservas.add(new Reserva(rs1.getString(1), rs1.getTimestamp(2), rs1.getTimestamp(3), rs1.getString(4),
						rs1.getString(5)));
			}
			while (rs.next()) {
				salas.add(new Sala(rs.getString(1), rs.getString(2), rs.getDouble(3), reservas));
			}
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
				salas.add(new Sala(rs.getString(1), rs.getString(2), rs.getDouble(3), null));
			}
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
					"SELECT R.ID_RESERVA FROM RESERVA r WHERE r.CODIGO_SALA = ? AND r.HORA_INICIO = ?");
			ps.setString(1, codigoSala);
			ps.setTimestamp(2, horaInicio);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Devuelve verdadero si esta no tiene reserva simultanea y false en caso contrario.
	 * @param id_usuario
	 * @param horaInicio
	 * @return
	 */
	public boolean comprobarReservaSimultaneaUsuario(String id_usuario, Timestamp horaInicio) {
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"SELECT R.ID_RESERVA FROM RESERVA r WHERE r.ID_USUARIO = ? AND r.HORA_INICIO = ?");
			ps.setString(1, id_usuario);
			ps.setTimestamp(2, horaInicio);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
