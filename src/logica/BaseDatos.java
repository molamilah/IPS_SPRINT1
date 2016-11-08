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
	public class ExcepcionUsuarioNoEncontrado extends Exception {
		public ExcepcionUsuarioNoEncontrado(String msg) {
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

	private Connection conectar() {
		try {
			conexion = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexion;
	}

	public boolean comprobarUsuario(int user) throws ExcepcionUsuarioNoEncontrado {
		Usuario usuario = cargarUsuario(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
		} else
			return true;
	}

	public boolean comprobarUserPassword(int user, String password) throws Exception {
		Usuario usuario = cargarUsuario(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
		}
		if (usuario.getPassword().equals(password))
			return true;
		else
			throw new ExceptionUserPassword("EL usuario y la contrase�a no coinciden.");
	}

	/**
	 * Comprobar administrador y contrase�a.
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws ExcepcionUsuarioNoEncontrado
	 * @throws ExceptionUsuarioContrase�a
	 */
	public boolean comprobarUserPasswordAdmin(String user, String password)
			throws ExcepcionUsuarioNoEncontrado, ExceptionUserPassword {
		Usuario usuario = cargarAdministrador(user);
		if (usuario == null) {
			throw new ExcepcionUsuarioNoEncontrado("El usuario no existe en la base de datos");
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
				user = new Usuario(user_id, dni, nombre, apellidos, direccion, email, ciudad, password, cuota, baja,
						fecha_baja);
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
				user = new Usuario(user_id, dni, nombre, apellidos, direccion, email, ciudad, password, cuota, baja,
						fecha_baja);
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

			PreparedStatement ps1 = con.prepareStatement(
					"SELECT * FROM RESERVA R WHERE ID_SALA = ? and (R.ESTADO<>'CANCELADA' or r.estado IS NULL)");
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
			PreparedStatement ps = con
					.prepareStatement("SELECT R.ID_RESERVA FROM RESERVA r WHERE r.ID_SALA = ? AND r.HORA_INICIO = ?");
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

	private int findID_Usuario(String DNI) throws ExcepcionUsuarioNoEncontrado {
		Connection con = null;
		PreparedStatement psID_USUARIO = null;
		ResultSet rsID_USUARIO = null;
		int idUsuario = 0;
		try {
			con = conectar();
			psID_USUARIO = con.prepareStatement("select id_usuario from usuario where DNI = ?");
			psID_USUARIO.setString(1, DNI);
			rsID_USUARIO = psID_USUARIO.executeQuery();
			if (rsID_USUARIO.next()) {
				idUsuario = rsID_USUARIO.getInt(1);
				rsID_USUARIO.close();
				psID_USUARIO.close();
				con.close();
			} else {
				rsID_USUARIO.close();
				psID_USUARIO.close();
				con.close();
				throw new ExcepcionUsuarioNoEncontrado("El usuario no se encuentra en la base de datos");
			}
			return idUsuario;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idUsuario;
	}

	private int findID_Usuario(int idReserva) {
		Connection con = null;
		PreparedStatement psID_USUARIO = null;
		ResultSet rsID_USUARIO = null;
		int idUsuario = 0;
		try {
			con = conectar();

			psID_USUARIO = con.prepareStatement("select id_usuario from reserva where id_reserva = ?");
			psID_USUARIO.setInt(1, idReserva);
			rsID_USUARIO = psID_USUARIO.executeQuery();
			rsID_USUARIO.next();
			idUsuario = rsID_USUARIO.getInt(1);

			rsID_USUARIO.close();
			psID_USUARIO.close();
			con.close();

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
			psID_RESERVA = con
					.prepareStatement("select id_reserva from reserva " + "where id_usuario = ? and hora_inicio = ?");
			psID_RESERVA.setInt(1, id_usuario);
			psID_RESERVA.setTimestamp(2, fecha);
			rsID_RESERVA = psID_RESERVA.executeQuery();
			if (rsID_RESERVA.next()) {
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

	private int findID_Reserva(int id_pago) {
		Connection con = null;
		PreparedStatement psID_RESERVA = null;
		ResultSet rsID_RESERVA = null;

		int idReserva = 0;

		try {
			con = conectar();
			psID_RESERVA = con.prepareStatement("select id_reserva from pago where id_pago = ?");
			psID_RESERVA.setInt(1, id_pago);
			rsID_RESERVA = psID_RESERVA.executeQuery();
			rsID_RESERVA.next();
			idReserva = rsID_RESERVA.getInt(1);
			rsID_RESERVA.close();
			psID_RESERVA.close();
			con.close();

			return idReserva;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idReserva;
	}

	private int findID_Pago(int idReserva) throws ExcepcionPagoNoEncontrado {
		Connection con = null;
		PreparedStatement psID_PAGO = null;
		ResultSet rsID_PAGO = null;

		int idPago = 0;

		try {
			con = conectar();
			psID_PAGO = con.prepareStatement("select id_pago from pago where id_reserva = ?");
			psID_PAGO.setInt(1, idReserva);
			rsID_PAGO = psID_PAGO.executeQuery();
			if (rsID_PAGO.next()) {
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

	private Timestamp findFechaReserva(int id_reserva) {
		Connection con = null;
		PreparedStatement psFECHARESERVA = null;
		ResultSet rsFECHARESERVA = null;

		Timestamp fechaReserva = null;

		try {
			con = conectar();
			psFECHARESERVA = con.prepareStatement("select hora_inicio from reserva where id_reserva = ?");
			psFECHARESERVA.setInt(1, id_reserva);
			rsFECHARESERVA = psFECHARESERVA.executeQuery();
			rsFECHARESERVA.next();
			fechaReserva = rsFECHARESERVA.getTimestamp(1);
			rsFECHARESERVA.close();
			psFECHARESERVA.close();
			con.close();

			return fechaReserva;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechaReserva;
	}

	private double findCuotaBase(int idUsuario) {
		Connection con = null;
		PreparedStatement psCUOTABASE = null;
		ResultSet rsCUOTABASE = null;

		double cuotaBase = 0.0;

		try {
			con = conectar();
			psCUOTABASE = con.prepareStatement("select cuota from usuario where id_usuario = ?");
			psCUOTABASE.setInt(1, idUsuario);
			rsCUOTABASE = psCUOTABASE.executeQuery();
			rsCUOTABASE.next();
			cuotaBase = rsCUOTABASE.getDouble(1);
			rsCUOTABASE.close();
			psCUOTABASE.close();
			con.close();

			return cuotaBase;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cuotaBase;
	}

	private boolean verificarSiExisteLaMensualidad(int idUsuario, int mes, int anno) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		boolean existe = false;

		try {
			con = conectar();
			pst = con.prepareStatement("select * from mensualidades where id_usuario = ? and mes = ? and anno = ?");
			pst.setInt(1, idUsuario);
			pst.setInt(2, mes);
			pst.setInt(3, anno);
			rs = pst.executeQuery();
			if (rs.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	public void pasarPagoAPagado(String DNI, Timestamp fecha)
			throws ExcepcionUsuarioNoEncontrado, ExcepcionReservaNoEncontrada, ExcepcionPagoNoEncontrado, IOException {
		Connection con = null;
		PreparedStatement psUPDATE_PAGO = null;

		int id_usuario = 0;
		int id_reserva = 0;
		int id_pago = 0;

		try {
			con = conectar();
			id_usuario = findID_Usuario(DNI);
			id_reserva = findID_Reserva(id_usuario, fecha);

			if (comprobarPagoEnCuota(findID_Pago(id_reserva))) {
				descontarPagoACuota(findID_Pago(id_reserva));
			}

			psUPDATE_PAGO = con.prepareStatement("update pago set pagado = true where id_reserva = ?");
			psUPDATE_PAGO.setInt(1, id_reserva);
			int num = psUPDATE_PAGO.executeUpdate();
			if (num == 0) {
				throw new ExcepcionPagoNoEncontrado("El pago no existe");
			}
			id_pago = findID_Pago(id_reserva);
			generarRecibo(id_pago, DNI, fecha);

			psUPDATE_PAGO.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private double sacarImportePago(int idPago) throws SQLException {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return importe;
	}

	private void generarRecibo(int id_pago, String DNI, Timestamp fecha) throws IOException, SQLException {
		int idPago = id_pago;
		double importe = sacarImportePago(idPago);

		String ruta = id_pago + ".txt";
		File archivo = new File(ruta);
		BufferedWriter bw;
		if (archivo.exists()) {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write("Número de recibo: " + idPago + "\n");
			bw.write("El socio con DNI: " + DNI + " ha pagado la reserva con fecha " + fecha + ".\n");
			bw.write("Importe a pagar: " + importe + " euros.\n");
		} else {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write("Número de recibo: " + idPago + "\n");
			bw.write("El socio con DNI: " + DNI + " ha pagado la reserva con fecha " + fecha + ". \n");
			bw.write("Importe a pagar: " + importe + " euros. \n");
		}
		bw.close();
	}

	private void actualizarMensualidad(int idUsuario, int mes, int anno, double importe)
			throws ExcepcionPagoNoEncontrado {

		Connection con = null;
		PreparedStatement pst = null;

		try {

			con = conectar();
			pst = con.prepareStatement("update mensualidades set cuota = cuota + ?");
			pst.setDouble(1, importe);
			int num = pst.executeUpdate();
			if (num == 0) {
				throw new ExcepcionPagoNoEncontrado(
						"Hubo un error al actualizar la mensualidad (" + idUsuario + "," + mes + "," + anno + ").");
			}

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void registrarMensualidad(int idUsuario, int mes, int anno, double importe, double cuota)
			throws ExcepcionPagoNoEncontrado {

		Connection con = null;
		PreparedStatement pst = null;

		try {

			con = conectar();
			pst = con.prepareStatement("insert into mensualidades values(?, ?, ?, ?)");
			pst.setInt(1, idUsuario);
			pst.setInt(2, mes);
			pst.setInt(3, anno);
			pst.setDouble(4, importe + cuota);
			int num = pst.executeUpdate();
			if (num == 0) {
				throw new ExcepcionPagoNoEncontrado(
						"Hubo un error al crear la mensualidad (" + idUsuario + "," + mes + "," + anno + ").");
			}

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void registrarPagoEnCuota(int idPago) throws ExcepcionPagoNoEncontrado {

		Connection con = null;
		PreparedStatement pst = null;

		try {

			con = conectar();
			pst = con.prepareStatement("update pago set en_cuota = true where id_pago = ?");
			pst.setInt(1, idPago);
			int num = pst.executeUpdate();
			if (num == 0) {
				throw new ExcepcionPagoNoEncontrado(
						"Hubo un error al registrar el pago (" + idPago + ") en la cuota mensual.");
			}

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void actualizarMensualidades() throws SQLException, ExcepcionPagoNoEncontrado {

		// Miro los pagos con CONTADO a false y que no estén en PAGADO.
		List<Integer> idsPagos = recuperarPagosParaCuotaMensual();
		// List<Map<String, Integer>> listaMapIDPAGO_RESERVA_SOCIO = new
		// ArrayList<Map<String, Integer>>(); // Por si acaso necesito
		// relacionar ID_PAGO con su ID_RESERVA
		Integer idReserva = 0;
		Timestamp fecha = null;
		Integer idUsuario = 0;
		double importe = 0.0;
		double cuota = 0.0;

		// Por cada pago:
		for (Integer idPago : idsPagos) {
			// Miro el ID de su reserva y cojo la fecha, el ID del usuario,
			// la cuota base y el importe.
			idReserva = findID_Reserva(idPago);
			fecha = findFechaReserva(idReserva);
			idUsuario = findID_Usuario(idReserva);
			cuota = findCuotaBase(idUsuario);
			importe = sacarImportePago(idPago);
			// Miro el día y el mes:
			// - Si es después del 20, sumo +1 al mes. Si es antes del 19, no
			// sumo +1 al mes.
			if (fecha.getDate() >= 20) {
				fecha.setMonth(fecha.getMonth() + 1);
			}
			// Por ahora tengo el año, el mes y el ID del usuario.
			// Miro si existe la mensualidad y luego se presentan dos casos:
			// - Si existe esa mensualidad, sumo el importe del pago a la cuota.
			if (verificarSiExisteLaMensualidad(idUsuario, fecha.getMonth(), fecha.getYear() + 1900)) {
				actualizarMensualidad(idUsuario, fecha.getMonth(), fecha.getYear() + 1900, importe);
			} else { // - Si no existe, creo la mensualidad sumando el importe y
						// la cuota.
				registrarMensualidad(idUsuario, fecha.getMonth(), fecha.getYear() + 1900, importe, cuota);
			}
			// Una vez acabado esto, en el pago pongo el campo EN_CUOTA a true.
			registrarPagoEnCuota(idPago);
		}

	}

	private List<Integer> recuperarPagosParaCuotaMensual() throws SQLException, ExcepcionPagoNoEncontrado {
		List<Integer> ids = new ArrayList<Integer>();

		Connection con = null;
		PreparedStatement psIDS = null;
		ResultSet rsIDS = null;

		try {
			con = conectar();
			psIDS = con.prepareStatement(
					"SELECT ID_PAGO FROM PAGO WHERE CONTADO = FALSE AND EN_CUOTA = FALSE AND PAGADO = false");
			rsIDS = psIDS.executeQuery();

			if (rsIDS.next()) {
				ids.add(rsIDS.getInt(1));
				while (rsIDS.next()) {
					ids.add(rsIDS.getInt(1));
				}
				rsIDS.close();
				psIDS.close();
				con.close();
			} else {
				rsIDS.close();
				psIDS.close();
				con.close();
				throw new ExcepcionPagoNoEncontrado("No hay pagos que añadir a las cuotas mensuales.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public void borrarPagoReserva(int idReserva) throws ExcepcionPagoNoEncontrado {
		Connection con = null;
		PreparedStatement psIdPago = null;
		int idPago = 0;

		try {
			con = conectar();
			idPago = findID_Pago(idReserva);
			if (comprobarPagoEnCuota(idPago)) {
				descontarPagoACuota(idPago);
			}
			psIdPago = con.prepareStatement("DELETE FROM PAGO WHERE id_pago = ?");
			psIdPago.setInt(1, idPago);
			psIdPago.executeUpdate();

			psIdPago.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean comprobarPagoEnCuota(int idPago) {
		Connection con = null;
		PreparedStatement psCuota = null;
		ResultSet rsCuota = null;
		boolean existe = false;

		try {
			con = conectar();
			psCuota = con.prepareStatement("select en_cuota from pago where id_pago = ?");
			psCuota.setInt(1, idPago);
			rsCuota = psCuota.executeQuery();

			if (rsCuota.next()) {
				existe = rsCuota.getBoolean(1);
			}

			rsCuota.close();
			psCuota.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;

	}

	@SuppressWarnings("deprecation")
	private void descontarPagoACuota(int idPago) {
		Connection con = null;
		PreparedStatement pst = null;

		double importe = 0.0;
		int idReserva = 0;
		int idUsuario = 0;
		Timestamp fecha = null;

		try {
			importe = sacarImportePago(idPago);
			idReserva = findID_Reserva(idPago);
			idUsuario = findID_Usuario(idReserva);
			fecha = findFechaReserva(idReserva);
			con = conectar();
			pst = con.prepareStatement(
					"update mensualidades set cuota = (cuota-?) where id_usuario = ? and mes = ? and anno = ?");
			pst.setDouble(1, importe);
			pst.setInt(2, idUsuario);
			if (fecha.getDate() >= 20) {
				pst.setInt(3, fecha.getMonth() + 1);
			} else {
				pst.setInt(3, fecha.getMonth());
			}
			pst.setInt(4, fecha.getYear() + 1900);
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recupera de la base de datos las reservas de un determinado
	 * usuario que todavia no han pasado.
	 * 
	 * @param user
	 * @param fechaActual
	 * @param fechaHasta
	 * @return
	 */
	public List<String> cargarReservasPendientesUsuario(Usuario user, Timestamp fechaActual, Timestamp fechaHasta) {
		List<String> reservasPendientes = new ArrayList<String>();
		try {
			Connection con = conectar();
			// Falta a�adir la comprobacion de que no este cancelada.
			PreparedStatement ps = con.prepareStatement(
					"SELECT S.DESCRIPCION,R.HORA_INICIO,R.HORA_FIN FROM RESERVA R,SALA S WHERE S.ID_SALA = R.ID_SALA AND R.ID_USUARIO = ? AND R.HORA_INICIO BETWEEN ? AND ? and (R.ESTADO<>'CANCELADA' or r.estado IS NULL) ORDER BY R.HORA_INICIO;");
			ps.setInt(1, user.getId_usuario());
			ps.setTimestamp(2, fechaActual);
			ps.setTimestamp(3, fechaHasta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservasPendientes.add(rs.getString(1) + ";" + rs.getString(2) + ";" + rs.getString(3));
			}
			rs.close();
			ps.close();
			con.close();
			return reservasPendientes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que recupera de la base de datos las reservas que ha realizado un
	 * usuario con independencia de que hayan o no pasado o esten o no
	 * canceladas.
	 * 
	 * @param user
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<String> cargarReservasRealizadasUsuario(Usuario user, Timestamp fechaDesde, Timestamp fechaHasta) {
		List<String> reservasPendientes = new ArrayList<String>();
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"SELECT S.DESCRIPCION,R.HORA_INICIO,R.HORA_FIN FROM RESERVA R,SALA S WHERE S.ID_SALA = R.ID_SALA AND R.ID_USUARIO = ? AND R.HORA_INICIO BETWEEN ? AND ? ORDER BY R.HORA_INICIO;");
			ps.setInt(1, user.getId_usuario());
			ps.setTimestamp(2, fechaDesde);
			ps.setTimestamp(3, fechaHasta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservasPendientes.add(rs.getString(1) + ";" + rs.getString(2) + ";" + rs.getString(3));
			}
			rs.close();
			ps.close();
			con.close();
			return reservasPendientes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que recupera de la base de datos las reservas de un usuario que
	 * han sido canceladas.
	 * 
	 * @param user
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<String> cargarReservasCanceladasUsuario(Usuario user, Timestamp fechaDesde, Timestamp fechaHasta) {
		List<String> reservasPendientes = new ArrayList<String>();
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"SELECT S.DESCRIPCION,R.HORA_INICIO,R.HORA_FIN FROM RESERVA R,SALA S WHERE S.ID_SALA = R.ID_SALA AND R.ID_USUARIO = ? AND R.ESTADO = 'CANCELADA' AND R.HORA_INICIO BETWEEN ? AND ? ORDER BY R.HORA_INICIO;");
			ps.setInt(1, user.getId_usuario());
			ps.setTimestamp(2, fechaDesde);
			ps.setTimestamp(3, fechaHasta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reservasPendientes.add(rs.getString(1) + ";" + rs.getString(2) + ";" + rs.getString(3));
			}
			rs.close();
			ps.close();
			con.close();
			return reservasPendientes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que cambia el estado de una reserva a cancelado.
	 * 
	 * @param user
	 * @param fechaDesde
	 * @param fechaHasta
	 * @throws ExcepcionReservaNoEncontrada
	 * @throws ExcepcionPagoNoEncontrado
	 */
	public void cancelarReservaUsuario(Usuario user, String nombreSala, String fechaDesde, String fechaHasta)
			throws ExcepcionPagoNoEncontrado, ExcepcionReservaNoEncontrada {
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(
					"UPDATE RESERVA R SET ESTADO ='CANCELADA' WHERE R.ID_USUARIO=? AND R.HORA_INICIO = ? AND R.HORA_FIN = ? and R.ID_SALA = ?");
			ps.setInt(1, user.getId_usuario());
			ps.setString(2, fechaDesde);
			ps.setString(3, fechaHasta);
			int i = cargarIDSalaNombre(nombreSala);
			ps.setInt(4, i);
			ps.executeUpdate();

			borrarPagoReserva(findID_Reserva(user.getId_usuario(), Timestamp.valueOf(fechaDesde)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int cargarIDSalaNombre(String nombre) {
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement("SELECT ID_SALA FROM SALA S WHERE S.DESCRIPCION=?");
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("ID_SALA");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}