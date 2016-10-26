package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	public class ExceptionUserPassword extends Exception {
		public ExceptionUserPassword(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public class ExcepcionUserNotFound extends Exception {
		public ExcepcionUserNotFound(String msg) {
			super(msg);
		}

	}
	
	@SuppressWarnings("serial")
	public class ExcepcionReservaNoEncontrada extends Exception {
		public ExcepcionReservaNoEncontrada(String msg) {
			super(msg);
		}

	}
	
	@SuppressWarnings("serial")
	public class ExcepcionPagoNoEncontrado extends Exception {
		public ExcepcionPagoNoEncontrado(String msg) {
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
			throw new ExcepcionUserNotFound("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUserPassword("EL usuario y la contrase�a no coinciden.");
	}
	
	/**
	 * Comprobar administrador y contrase�a.
	 * @param user
	 * @param password
	 * @return
	 * @throws ExcepcionUsuarioNoEncontrado
	 * @throws ExceptionUsuarioContrase�a
	 */
	public boolean comprobarUserPasswordAdmin(String user, String password)
			throws ExcepcionUserNotFound, ExceptionUserPassword {
		Usuario usuario = cargarAdministrador(user);
		if (usuario == null) {
			throw new ExcepcionUserNotFound("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUserPassword("EL usuario y la contrase�a no coinciden.");
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
	
	private int findID_Usuario(String DNI) throws ExcepcionUserNotFound {
		Connection con = null;
		PreparedStatement psID_USUARIO = null;
		ResultSet rsID_USUARIO = null;
		int idUsuario = 0;
		try {
			con = conectar();
			psID_USUARIO = con.prepareStatement("select id_usuario from usuario where DNI = ?");
			psID_USUARIO.setString(1, DNI);
			rsID_USUARIO = psID_USUARIO.executeQuery();
			if(rsID_USUARIO.next()) {
				idUsuario = rsID_USUARIO.getInt(1);
				rsID_USUARIO.close();
				psID_USUARIO.close();
				con.close();
			} else {
				rsID_USUARIO.close();
				psID_USUARIO.close();
				con.close();
				throw new ExcepcionUserNotFound("El usuario no se encuentra en la base de datos");
			}
			return idUsuario;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idUsuario;
	}
	
	private int findID_Reserva(int id_usuario, Timestamp fecha) throws ExcepcionReservaNoEncontrada {
		Connection con = null;
		PreparedStatement psID_RESERVA = null;
		ResultSet rsID_RESERVA = null;
		
		int idReserva = 0;
		
		try {
			con = conectar();
			psID_RESERVA = con.prepareStatement("select id_reserva from reserva "
					+ "where id_usuario = ? and hora_inicio = ?");
			psID_RESERVA.setInt(1, id_usuario);
			psID_RESERVA.setTimestamp(2, fecha);
			rsID_RESERVA = psID_RESERVA.executeQuery();
			if(rsID_RESERVA.next()) {
				idReserva = rsID_RESERVA.getInt(1);
				rsID_RESERVA.close();
				psID_RESERVA.close();
				con.close();
			} else {
				rsID_RESERVA.close();
				psID_RESERVA.close();
				con.close();
				throw new ExcepcionReservaNoEncontrada("La reserva no se encuentra en la base de datos");
			}
			return idReserva;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idReserva;
	}
	
	private int findID_Pago( int idReserva) throws ExcepcionPagoNoEncontrado {
		Connection con = null;
		PreparedStatement psID_PAGO = null;
		ResultSet rsID_PAGO = null;
		
		int idPago = 0;
		
		try {
			con = conectar();
			psID_PAGO = con.prepareStatement("select id_pago from pago where id_reserva = ?");
			psID_PAGO.setInt(1, idReserva);
			rsID_PAGO = psID_PAGO.executeQuery();
			if(rsID_PAGO.next()) {
				idPago = rsID_PAGO.getInt(1);
				rsID_PAGO.close();
				psID_PAGO.close();
				con.close();
			} else {
				rsID_PAGO.close();
				psID_PAGO.close();
				con.close();
				throw new ExcepcionPagoNoEncontrado("El pago no se encuentra en la base de datos");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPago;
	}
	
	public void pasarPagoAPagado( String DNI, Timestamp fecha ) throws ExcepcionUserNotFound, ExcepcionReservaNoEncontrada, ExcepcionPagoNoEncontrado, IOException {
		Connection con = null;		
		PreparedStatement psUPDATE_PAGO = null;
		
		int id_usuario = 0;
		int id_reserva = 0;
		int id_pago = 0;
		
		try {
			con = conectar();
			id_usuario = findID_Usuario(DNI);
			id_reserva = findID_Reserva(id_usuario, fecha);
			
			psUPDATE_PAGO = con.prepareStatement("update pago set status = ? where id_reserva = ?");
			psUPDATE_PAGO.setString(1, "pagado");
			psUPDATE_PAGO.setInt(2, id_reserva);
			int num = psUPDATE_PAGO.executeUpdate();
			if( num == 0) {
				throw new ExcepcionPagoNoEncontrado("El pago no existe");
			}
			id_pago = findID_Pago(id_reserva);
			generarRecibo(id_pago, DNI, fecha);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private double sacarImportePago(int idPago) {
		Connection con = null;
		PreparedStatement psIMPORTE_PAGO = null;
		ResultSet rsIMPORTE_PAGO = null;
		
		double importe = 0.0;
		
		try {
			con = conectar();
			psIMPORTE_PAGO = con.prepareStatement("select precio from pago where id_pago = ?");
			psIMPORTE_PAGO.setInt(1, idPago);
			rsIMPORTE_PAGO = psIMPORTE_PAGO.executeQuery();
			rsIMPORTE_PAGO.next();
			importe = rsIMPORTE_PAGO.getDouble(1);
			
			rsIMPORTE_PAGO.close();
			psIMPORTE_PAGO.close();
			con.close();
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		return importe;
	}
	
	private void generarRecibo( int id_pago, String DNI, Timestamp fecha) throws IOException {
		int idPago = id_pago;
		double importe = sacarImportePago(idPago);
		
		String ruta = id_pago + ".txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("N�mero de recibo: " + idPago);
            bw.write("El socio con DNI: " + DNI + " ha pagado la reserva con fecha " + fecha + ".");
            bw.write("Importe a pagar: " + importe + ".");
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("N�mero de recibo: " + idPago);
            bw.write("El socio con DNI: " + DNI + " ha pagado la reserva con fecha " + fecha + ".");
            bw.write("Importe a pagar: " + importe + ".");
        }
        bw.close();
	}
	
}