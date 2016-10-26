package reservas;

import java.util.Calendar;
import java.sql.*;

public class BBDDReservas {
	public static int id;
	private static String PASS = "";
	private static String USER = "SA";
	private static String URL = "jdbc:hsqldb:hsql://localhost/labdb";
	private static Connection conexion;

	private static void conectar() {
		try {
			conexion = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean comprobarDisponibilidadSocio(Calendar fechaInicial, Calendar fechaFinal) {
		conectar();
		PreparedStatement ps;
		ResultSet rs;
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);

		try {
			ps = conexion.prepareStatement("select id_reserva from Reserva where id_usuario = ? "
					+ "and ((? > hora_inicio and ? < hora_fin) or ( ? < hora_inicio and ? > hora_inicio) or (? = hora_inicio and ? = hora_fin))");
			ps.setTimestamp(2, horaInicial);
			ps.setTimestamp(3, horaInicial);
			ps.setTimestamp(4, horaInicial);
			ps.setTimestamp(5, horaFinal);
			ps.setTimestamp(6, horaInicial);
			ps.setTimestamp(7, horaFinal);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs.close();
				ps.close();
				return true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			return false;
		}
		return false;

	}

	public static boolean comprobarDisponibilidadInstalacion(int idInstalacion, Calendar fechaInicial,
			Calendar fechaFinal) {
		conectar();
		PreparedStatement ps;
		ResultSet rs;
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);

		try {
			ps = conexion.prepareStatement("select id_reserva from Reserva where id_sala = ? "
					+ "and ((? > hora_inicio and ? < hora_fin) or (? < hora_inicio and ?> hora_inicio) or (? = hora_inicio and ? = hora_fin))");
			ps.setTimestamp(2, horaInicial);
			ps.setTimestamp(3, horaInicial);
			ps.setTimestamp(4, horaInicial);
			ps.setTimestamp(5, horaFinal);
			ps.setTimestamp(6, horaInicial);
			ps.setTimestamp(7, horaFinal);
			ps.setInt(1, idInstalacion);
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs.close();
				ps.close();
				return true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			return false;
		}
		return false;

	}

	public static void hacerReserva(int idInstalacion, Calendar fechaInicial, Calendar fechaFinal) {
		conectar();
		PreparedStatement ps;
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);
		try {
			ps = conexion.prepareStatement("insert into Reserva (hora_inicio, hora_fin,"
					+ " id_usuario, id_sala, hora_entrada, hora_salida) values (?,?,?,?,null,null)");
			ps.setTimestamp(1, horaInicial);
			ps.setTimestamp(2, horaFinal);
			ps.setInt(3, id);
			ps.setInt(4, idInstalacion);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static java.util.Date comprobarBaja() {
		conectar();
		PreparedStatement ps;
		ResultSet rs;
		java.util.Date fechaBaja = null;
		try {
			ps = conexion.prepareStatement("select fecha_baja from Usuario where id_usuario = ? and baja = true");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				fechaBaja = new java.util.Date(rs.getTimestamp("fecha_baja").getTime());
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechaBaja;

	}

	public static double buscarPrecioInstalacion(int idSala) {
		conectar();
		PreparedStatement ps;
		ResultSet rs;
		double res = 0.0;
		try {
			ps = conexion.prepareStatement("select precio from Sala where id_sala = ?");
			ps.setInt(1, idSala);
			rs = ps.executeQuery();
			rs.next();
			res = rs.getDouble("precio");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static void validarPago(double precio, boolean tipoPago) {
		conectar();
		PreparedStatement ps;
		ResultSet rs2;
		Statement st;
		int idReserva = 0;
		try {
			st = conexion.createStatement();
			rs2 = st.executeQuery("select MAX(id_reserva) as id from Reserva");
			rs2.next();
			idReserva = rs2.getInt("id");
			rs2.close();
			st.close();
			ps = conexion.prepareStatement("insert into Pago (id_reserva, precio, contado, pagado) values(?,?,?,false)");
			ps.setInt(1, idReserva);
			ps.setDouble(2, precio);
			ps.setBoolean(3, tipoPago);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static int buscarInstalacion(String idSala) {
		conectar();
		ResultSet rs;
		PreparedStatement ps;
		int sala= -1;
		try {
			ps= conexion.prepareStatement("select id_sala from Sala where descripcion= ?");
			ps.setString(1,idSala);
			rs= ps.executeQuery();
			rs.next();
			sala = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sala;
	}

}
