package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;

public class BBDDReservasActividades {
	public static int id;
	private static String PASS = "";
	private static String USER = "SA";
	private static String URL = "jdbc:hsqldb:hsql://localhost/labdb";
	private static Connection conexion;

	public static void conectar() {
		try {
			conexion = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void desconectar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int crearActividad(String nombre, String descripcion, int plazas) {
		PreparedStatement ps;
		PreparedStatement ps1;
		ResultSet rs;
		int res = -1;
		try {
			ps = conexion.prepareStatement("insert into Actividades(nombre,descripcion,numero_plazas) values (?,?,?)");
			ps1 = conexion.prepareStatement("select MAX(id_actividad) as id from Actividades");
			ps.setString(1, nombre);
			ps.setString(2, descripcion);
			ps.setInt(3, plazas);
			ps.executeUpdate();
			ps.close();
			rs = ps1.executeQuery();
			rs.next();
			res = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;

	}

	public static void borrarActividad(int idActividad) {
		PreparedStatement ps;
		try {
			ps = conexion.prepareStatement("delete from Actividades where id_actividad = ?");
			ps.setInt(1, idActividad);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean comprobarDisponibilidadSocio(Calendar fechaInicial, Calendar fechaFinal) {

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

	public static ArrayList<Integer> comprobarDisponibilidadInstalacion(int idInstalacion, Calendar fechaInicial,
			Calendar fechaFinal) {
		ArrayList<Integer> conflictos = new ArrayList<Integer>();
		PreparedStatement ps;
		ResultSet rs;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);

		try {
			ps = conexion.prepareStatement("select id_reserva from Reserva where id_sala = ? "
					+ "and ((? > hora_inicio and ? < hora_fin) or (? < hora_inicio and ?> hora_inicio)"
					+ " or (? <= hora_inicio and ? >= hora_fin)) and estado IS NULL");
			ps.setInt(1, idInstalacion);
			ps.setTimestamp(2, horaInicial);
			ps.setTimestamp(3, horaInicial);
			ps.setTimestamp(4, horaInicial);
			ps.setTimestamp(5, horaFinal);
			ps.setTimestamp(6, horaInicial);
			ps.setTimestamp(7, horaFinal);
			rs = ps.executeQuery();
			while (rs.next())
				conflictos.add(rs.getInt("id_reserva"));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conflictos;

	}

	public static ArrayList<Integer> buscarConflicto(int idInstalacion, Calendar fechaInicial, Calendar fechaFinal) {

		ArrayList<Integer> res = new ArrayList<Integer>();
		PreparedStatement ps;
		ResultSet rs;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);

		try {
			ps = conexion.prepareStatement("select id_reserva from Reserva where id_sala = ? "
					+ "and ((? > hora_inicio and ? < hora_fin) or (? < hora_inicio and ?> hora_inicio) or (? = hora_inicio and ? = hora_fin)) and estado IS NULL");
			ps.setTimestamp(2, horaInicial);
			ps.setTimestamp(3, horaInicial);
			ps.setTimestamp(4, horaInicial);
			ps.setTimestamp(5, horaFinal);
			ps.setTimestamp(6, horaInicial);
			ps.setTimestamp(7, horaFinal);
			ps.setInt(1, idInstalacion);
			rs = ps.executeQuery();
			while (rs.next())
				res.add(rs.getInt("id_reserva"));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;

	}

	public static void hacerReserva(int idInstalacion, Calendar fechaInicial, Calendar fechaFinal, int idActividad) {

		PreparedStatement ps;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		Timestamp horaInicial = new Timestamp(fechaInicial.getTimeInMillis());
		Timestamp horaFinal = new Timestamp(fechaFinal.getTimeInMillis());
		horaInicial.setNanos(0);
		horaFinal.setNanos(0);
		try {
			ps = conexion.prepareStatement("insert into Reserva (hora_inicio, hora_fin,"
					+ " id_usuario, id_sala, hora_entrada, hora_salida, id_actividad) values (?,?,?,?,null,null,?)");
			ps.setTimestamp(1, horaInicial);
			ps.setTimestamp(2, horaFinal);
			ps.setInt(3, id);
			ps.setInt(4, idInstalacion);
			if (idActividad == -1)
				ps.setNull(5, java.sql.Types.INTEGER);
			else
				ps.setInt(5, idActividad);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void resolverConflictos(ArrayList<Reserva> reservas, ArrayList<Object[]> reservasPendientes) {
		PreparedStatement ps;
		PreparedStatement ps1;
		PreparedStatement ps2;
		try {
			ps = conexion.prepareStatement("delete from Reserva where id_reserva = ?");
			ps1 = conexion.prepareStatement("delete from Pago where id_reserva = ?");
			for (Reserva r : reservas) {
				ps.setInt(1, r.getId_reserva());
				ps1.setInt(1, r.getId_reserva());
				ps1.executeUpdate();
				ps.executeUpdate();
			}
			ps1.close();
			ps.close();
			ps2 = conexion.prepareStatement("insert into Reserva (hora_inicio, hora_fin,"
					+ " id_usuario, id_sala, hora_entrada, hora_salida, id_actividad) values (?,?,0,?,null,null,?)");
			for (Object[] item : reservasPendientes) {
				Timestamp hora_inicio = new Timestamp(((Calendar) item[1]).getTimeInMillis());
				Timestamp hora_fin = new Timestamp(((Calendar) item[2]).getTimeInMillis());
				hora_inicio.setNanos(0);
				hora_fin.setNanos(0);
				ps2.setTimestamp(1, hora_inicio);
				ps2.setTimestamp(2, hora_fin);
				ps2.setInt(3, (Integer) item[0]);
				if ((Integer) item[3] != -1)
					ps2.setInt(4, (Integer) item[3]);
				else
					ps2.setNull(4, java.sql.Types.INTEGER);
				ps2.executeUpdate();
			}
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static java.util.Date comprobarBaja() {

		PreparedStatement ps;
		ResultSet rs;
		java.util.Date fechaBaja = null;
		try {
			ps = conexion.prepareStatement("select fecha_baja from Usuario where id_usuario = ? and baja = true");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				fechaBaja = new java.util.Date(rs.getTimestamp("fecha_baja").getTime());
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechaBaja;

	}

	public static double buscarPrecioInstalacion(int idSala) {

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
			ps = conexion
					.prepareStatement("insert into Pago (id_reserva, precio, contado, pagado) values(?,?,?,false)");
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

		ResultSet rs;
		PreparedStatement ps;
		int sala = -1;
		try {
			ps = conexion.prepareStatement("select id_sala from Sala where descripcion= ?");
			ps.setString(1, idSala);
			rs = ps.executeQuery();
			rs.next();
			sala = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sala;
	}

	public static String buscarDatosReserva(int idReserva) {
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs;
		ResultSet rs2;
		String res = "";
		try {
			ps = conexion.prepareStatement("select * from Reserva where id_reserva = ?");
			ps.setInt(1, idReserva);
			rs = ps.executeQuery();
			rs.next();
			ps2 = conexion.prepareStatement("select descripcion from Sala where id_sala = ?");
			ps2.setInt(1, rs.getInt("id_sala"));
			rs2 = ps2.executeQuery();
			rs2.next();
			res = ("Reserva: " + idReserva + "Cliente: " + rs.getInt("id_usuario") + "Sala: "
					+ rs2.getString("descripcion") + "HoraInicio: " + rs.getTimestamp("hora_Inicio") + "HoraFin: "
					+ rs.getTimestamp("hora_fin"));
			rs2.close();
			ps2.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;

	}

	public static ArrayList<Reserva> obtenerDatosReservas(ArrayList<Integer> conflictos) {
		ArrayList<Reserva> res = new ArrayList<Reserva>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = conexion.prepareStatement("select * from Reserva where id_reserva = ?");
			for (int id : conflictos) {
				ps.setInt(1, id);
				rs = ps.executeQuery();
				while (rs.next())
					res.add(new Reserva(rs.getInt("id_reserva"), rs.getInt("id_usuario"), rs.getInt("id_sala"),
							rs.getTimestamp("hora_inicio"), rs.getTimestamp("hora_fin"),
							rs.getTimestamp("hora_entrada"), rs.getTimestamp("hora_salida")));
				rs.close();
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String[] ObtenerDatosCliente(int id_usuario) {
		PreparedStatement ps;
		ResultSet rs;
		String[] res = new String[2];
		try {
			ps = conexion.prepareStatement("select nombre, apellidos, direccion from Usuario where id_usuario = ?");
			ps.setInt(1, id_usuario);
			rs = ps.executeQuery();
			rs.next();
			res[0] = rs.getString("Nombre") + " " + rs.getString("Apellidos");
			res[1] = rs.getString("direccion");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
