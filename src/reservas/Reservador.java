package reservas;

import java.util.ArrayList;
import java.util.Calendar;

import iguConflictos.VentanaConflictos;

public class Reservador {

	public static ArrayList<Boolean> errors = new ArrayList<Boolean>();
	public static ArrayList<Calendar> fechasReserva = new ArrayList<Calendar>();

	public static boolean reservarSocio(int idSocio, String idSala, int year, int mes, int day, int horaInicial,
			int horaFinal, boolean tipoPago) {
		Calendar fechaInicial = Calendar.getInstance();
		int month = fechaInicial.get(Calendar.MONTH) + mes;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		int idInstalacion = BBDDReservas.buscarInstalacion(idSala);

		BBDDReservas.id = idSocio;

		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month);
		fechaInicial.set(Calendar.DAY_OF_MONTH, day);

		fechaFinal.setTime(fechaInicial.getTime());
		fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
		fechaFinal.set(Calendar.HOUR_OF_DAY, horaFinal);
		if (BBDDReservas.comprobarDisponibilidadSocio(fechaInicial, fechaFinal)
				&& BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal)) {
			BBDDReservas.hacerReserva(idInstalacion, fechaInicial, fechaFinal);
			BBDDReservas.validarPago((calcularPrecio(idInstalacion, horaInicial, horaFinal)), tipoPago);
			return true;
		}

		return false;
	}

	public static void reservarPeriodico(int id, Calendar fechaInicio, Calendar fechaFin, int horaInicio, int horaFin,
			int diaSemana, String instalacion) {
		int idInstalacion = BBDDReservas.buscarInstalacion(instalacion);
		fechasReserva.clear();
		errors.clear();
		fechaInicio.set(Calendar.MINUTE, 0);
		fechaInicio.set(Calendar.SECOND, 0);
		fechaFin.set(Calendar.MINUTE, 0);
		fechaFin.set(Calendar.SECOND, 0);
		BBDDReservas.id = id;
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
			if (BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, c, c2)) {
				BBDDReservas.hacerReserva(idInstalacion, c, c2);
				errors.add(true);
			} else
				errors.add(false);
		}
		conflictosReservas(horaInicio, horaFin, idInstalacion);
	}

	public static void reservarAdmin(int id, String instalacion, int year, int mes, int day, int horaInicio,
			int horaFin) {
		int idInstalacion = BBDDReservas.buscarInstalacion(instalacion);
		fechasReserva.clear();
		errors.clear();
		BBDDReservas.id = id;
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
		if (BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal)) {
			BBDDReservas.hacerReserva(idInstalacion, fechaInicial, fechaFinal);
			errors.add(true);
		} else
			errors.add(false);
		conflictosReservas(horaInicio, horaFin, idInstalacion);
	}

	private static void conflictosReservas(int horaInicio, int horaFin, int idInstalacion) {
		for (int i = 0; i < errors.size(); i++)
			if (errors.get(i) == false) {
				Calendar aux = fechasReserva.get(i);
				VentanaConflictos vc = new VentanaConflictos(aux, horaInicio, horaFin, idInstalacion);
				vc.setLocationRelativeTo(null);
				vc.setModal(true);
				vc.setVisible(true);
			}
	}

	private static double calcularPrecio(int id, int horaInicial, int horaFinal) {
		int duracion = horaFinal - horaInicial;
		double precio = BBDDReservas.buscarPrecioInstalacion(id);
		return precio * duracion;

	}
}
