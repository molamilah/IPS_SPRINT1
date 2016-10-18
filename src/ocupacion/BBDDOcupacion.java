package ocupacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class BBDDOcupacion {
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

	public static boolean Llegada(int idSocio, int idInstalacion) {
		conectar();
		Calendar actual = Calendar.getInstance();
		Timestamp time = new Timestamp(actual.getTimeInMillis());
		time.setNanos(0);
		PreparedStatement ps;
		PreparedStatement ps2;
		try {
			ps = conexion.prepareStatement("update Sala set ocupada = true where id_sala = ?");
			ps.setInt(1, idInstalacion);
			ps.executeUpdate();
			ps.close();
			ps2 = conexion.prepareStatement("update Reserva set hora_entrada = ?, hora_salida = null  "
					+ "where id_usuario = ? and id_sala = ? and (? between hora_inicio and hora_fin)");
			ps2.setTimestamp(1, time);
			ps2.setInt(2, idSocio);
			ps2.setInt(3, idInstalacion);
			ps2.setTimestamp(4, time);
			ps2.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;

	}

	public static boolean Salida(int idSocio, int idInstalacion) {
		conectar();
		Calendar actual = Calendar.getInstance();
		Timestamp time = new Timestamp(actual.getTimeInMillis());
		time.setNanos(0);
		PreparedStatement ps;
		PreparedStatement ps2;
		try {
			ps = conexion.prepareStatement("update Sala set ocupada = false where id_sala = ?");
			ps.setInt(1, idInstalacion);
			ps.executeUpdate();
			ps.close();
			ps2 = conexion.prepareStatement("update Reserva set hora_salida = ?  "
					+ "where id_usuario = ? and id_sala = ? and (? between hora_inicio and hora_fin) and ? > hora_entrada");
			ps2.setTimestamp(1, time);
			ps2.setInt(2, idSocio);
			ps2.setInt(3, idInstalacion);
			ps2.setTimestamp(4, time);
			ps2.setTimestamp(5, time);
			ps2.executeUpdate();
			ps2.close();
		} catch (SQLException e) {
			return false;
		}
		return true;

	}

}
