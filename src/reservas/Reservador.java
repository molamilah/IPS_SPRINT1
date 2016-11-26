package reservas;

import java.util.ArrayList;
import java.util.Calendar;

import iguConflictos.VentanaConflictos;
import logica.BBDDReservasActividades;
import logica.Reserva;

public class Reservador {
	public static ArrayList<Calendar> fechasReserva = new ArrayList<Calendar>();
	private static ArrayList<Integer> totalConflictos = new ArrayList<Integer>();
	private static ArrayList<Object[]> reservas = new ArrayList<Object[]>();

	public static boolean reservarSocio(int idSocio, String idSala, int year, int mes, int day, int horaInicial,
			int horaFinal, boolean tipoPago, int idActividad) {
		Calendar fechaInicial = Calendar.getInstance();
		int month = fechaInicial.get(Calendar.MONTH) + mes;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		int idInstalacion = BBDDReservasActividades.buscarInstalacion(idSala);

		BBDDReservasActividades.id = idSocio;

		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month);
		fechaInicial.set(Calendar.DAY_OF_MONTH, day);

		fechaFinal.setTime(fechaInicial.getTime());
		fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
		fechaFinal.set(Calendar.HOUR_OF_DAY, horaFinal);
		if (BBDDReservasActividades.comprobarDisponibilidadSocio(fechaInicial, fechaFinal)
				&& BBDDReservasActividades.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal).isEmpty()) {
			BBDDReservasActividades.hacerReserva(idInstalacion, fechaInicial, fechaFinal, idActividad);
			BBDDReservasActividades.validarPago((calcularPrecio(idInstalacion, horaInicial, horaFinal)), tipoPago);
			return true;
		}

		return false;
	}

	public static void reservarPeriodico(int id, Calendar fechaInicio, Calendar fechaFin, int horaInicio, int horaFin,
			int diaSemana, String instalacion, int idActividad) {
		int idInstalacion = BBDDReservasActividades.buscarInstalacion(instalacion);
		fechasReserva.clear();
		reservas.clear();
		totalConflictos.clear();
		fechaInicio.set(Calendar.MINUTE, 0);
		fechaInicio.set(Calendar.SECOND, 0);
		fechaFin.set(Calendar.MINUTE, 0);
		fechaFin.set(Calendar.SECOND, 0);
		BBDDReservasActividades.id = id;

		for (Calendar iter = fechaInicio; iter.before(fechaFin) || iter.equals(fechaFin); iter
				.add(Calendar.DAY_OF_MONTH, +1)) {
			if (iter.get(Calendar.DAY_OF_WEEK) == diaSemana) {
				fechasReserva.add((Calendar) iter.clone());
			}
		}
		for (Calendar c : fechasReserva) {
			Calendar c2 = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, horaInicio);
			c2.setTime(c.getTime());
			c2.set(Calendar.HOUR_OF_DAY, horaFin);
			ArrayList<Integer> conflictos = BBDDReservasActividades.comprobarDisponibilidadInstalacion(idInstalacion, c, c2);
			totalConflictos.addAll(conflictos);
			reservas.add(new Object[] { idInstalacion, c, c2, idActividad });
		}
		if (!totalConflictos.isEmpty())
			conflictosReservas();
		else {
			for (Object[] obj : reservas) {
				BBDDReservasActividades.hacerReserva((Integer) obj[0], (Calendar) obj[1], (Calendar) obj[2], (Integer) obj[3]);
			}
		}

	}

	public static void reservarAdmin(int id, String instalacion, int year, int mes, int day, int horaInicio,
			int horaFin, int idActividad) {
		int idInstalacion = BBDDReservasActividades.buscarInstalacion(instalacion);
		fechasReserva.clear();
		reservas.clear();
		totalConflictos.clear();
		BBDDReservasActividades.id = id;
		Calendar fechaInicial = Calendar.getInstance();
		int month = fechaInicial.get(Calendar.MONTH) + mes;
		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month);
		fechaInicial.set(Calendar.DAY_OF_MONTH, day);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.YEAR, year);
		fechaFinal.set(Calendar.MONTH, month);
		fechaFinal.set(Calendar.DAY_OF_MONTH, day);
		fechaInicial.set(Calendar.HOUR_OF_DAY, 0);
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		fechasReserva.add(fechaInicial);
		fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicio);
		fechaFinal.set(Calendar.HOUR_OF_DAY, horaFin);
		ArrayList<Integer> conflictos = BBDDReservasActividades.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial,
				fechaFinal);
		totalConflictos.addAll(conflictos);
		reservas.add(new Object[] { idInstalacion, fechaInicial, fechaFinal, idActividad });
		if (!totalConflictos.isEmpty())
			conflictosReservas();
		else
			BBDDReservasActividades.hacerReserva(idInstalacion, fechaInicial, fechaFinal, idActividad);
	}

	private static void conflictosReservas() {
		ArrayList<Reserva> reservasConflicto = BBDDReservasActividades.obtenerDatosReservas(totalConflictos);
		VentanaConflictos vc = new VentanaConflictos(reservasConflicto, reservas);
		vc.setLocationRelativeTo(null);
		vc.setModal(true);
		vc.setVisible(true);
	}

	private static double calcularPrecio(int id, int horaInicial, int horaFinal) {
		int duracion = horaFinal - horaInicial;
		double precio = BBDDReservasActividades.buscarPrecioInstalacion(id);
		return precio * duracion;

	}
}
