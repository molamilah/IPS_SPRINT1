package reservas;

import java.util.ArrayList;
import java.util.Calendar;

public class Reservador {

	public static ArrayList<Boolean> errors = new ArrayList<Boolean>();
	public static ArrayList<Calendar> fechasReserva = new ArrayList<Calendar>();

	public static boolean reservar(int idSocio, String idSala, int year, int mes, int day, int horaInicial,
			int horaFinal, boolean tipoPago) {
		Calendar fechaInicial = Calendar.getInstance();
		int month = fechaInicial.get(Calendar.MONTH) + mes + 1;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		int idInstalacion = BBDDReservas.buscarInstalacion(idSala);

		BBDDReservas.id = idSocio;
		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month - 1);
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

	public static boolean reservarAdministracion(String idSala, int year, int mes, int day, int horaInicial,
			int horaFinal) {
		Calendar fechaInicial = Calendar.getInstance();
		int month = fechaInicial.get(Calendar.MONTH) + mes + 1;
		fechaInicial.set(Calendar.MINUTE, 0);
		fechaInicial.set(Calendar.SECOND, 0);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.MINUTE, 0);
		fechaFinal.set(Calendar.SECOND, 0);
		int idInstalacion = BBDDReservas.buscarInstalacion(idSala);

		BBDDReservas.id = 0;

		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month - 1);
		fechaInicial.set(Calendar.DAY_OF_MONTH, day);

		fechaFinal.setTime(fechaInicial.getTime());
		fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
		fechaFinal.set(Calendar.HOUR_OF_DAY, horaFinal);
		if (BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal)) {
			BBDDReservas.hacerReserva(idInstalacion, fechaInicial, fechaFinal);
			return true;
		}

		return false;
	}

	public static void reservarPeriodico(Calendar fechaInicio, Calendar fechaFin, int horaInicio, int horaFin,
			int diaSemana, String instalacion) {
		int idInstalacion = BBDDReservas.buscarInstalacion(instalacion);
		fechasReserva.clear();
		errors.clear();
		BBDDReservas.id = 0;

		for (Calendar iter = fechaInicio; iter.before(fechaFin); iter.add(Calendar.DAY_OF_MONTH, +1)) {
			if (iter.get(Calendar.DAY_OF_WEEK) == diaSemana)
				fechasReserva.add(iter);
		}

		for (Calendar c : fechasReserva) {
			Calendar c2 = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, horaInicio);
			c2.set(Calendar.HOUR_OF_DAY, horaFin);
			if (BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, c, c2)) {
				BBDDReservas.hacerReserva(idInstalacion, c, c2);
				errors.add(true);
			} else
				errors.add(false);
		}

	}

	private static double calcularPrecio(int id, int horaInicial, int horaFinal) {
		int duracion = horaFinal - horaInicial;
		double precio = BBDDReservas.buscarPrecioInstalacion(id);
		return precio * duracion;

	}
}