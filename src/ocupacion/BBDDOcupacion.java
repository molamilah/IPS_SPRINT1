package ocupacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class BBDDOcupacion {
	private static String PASS = "";
	private static String USER = "SA";
	private static String URL = "jdbc:hsqldb:hsql://localhost/labdb";
	private static Connection conexion;

	private static void conectar() throws SQLException {
		conexion = DriverManager.getConnection(URL, USER, PASS);

	}

	public static boolean Llegada(String DNI) {
		Calendar actual = Calendar.getInstance();
		Timestamp time = new Timestamp(actual.getTimeInMillis());
		time.setNanos(0);
		int idSocio = -1;
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs;
		try {
			conectar();
			ps = conexion.prepareStatement("select id_usuario from Usuario where DNI = ?");
			ps.setString(1, DNI);
			rs = ps.executeQuery();
			rs.next();
			idSocio = rs.getInt("id_usuario");
			rs.close();
			ps.close();
			ps2 = conexion.prepareStatement("update Reserva set hora_entrada = ?, hora_salida = null  "
					+ "where id_usuario = ? and (? between hora_inicio and hora_fin)");
			ps2.setTimestamp(1, time);
			ps2.setInt(2, idSocio);
			ps2.setTimestamp(3, time);
			ps2.executeUpdate();
			ps2.close();
			conexion.close();
		} catch (SQLException e) {
			return false;
		}
		return true;

	}

	public static boolean Salida(String DNI) {
		Calendar actual = Calendar.getInstance();
		Timestamp time = new Timestamp(actual.getTimeInMillis());
		time.setNanos(0);
		int idSocio = -1;
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs;
		try {
			conectar();
			ps = conexion.prepareStatement("select id_usuario from Usuario where DNI = ?");
			ps.setString(1, DNI);
			rs = ps.executeQuery();
			rs.next();
			idSocio = rs.getInt("id_usuario");
			rs.close();
			ps.close();
			ps2 = conexion.prepareStatement("update Reserva set hora_salida = ?  "
					+ "where id_usuario = ? and (? between hora_inicio and hora_fin) and ? > hora_entrada");
			ps2.setTimestamp(1, time);
			ps2.setInt(2, idSocio);
			ps2.setTimestamp(3, time);
			ps2.setTimestamp(4, time);
			ps2.executeUpdate();
			ps2.close();
			conexion.close();
		} catch (SQLException e) {
			return false;
		}
		return true;

	}

}
