package reservas;

import java.util.Calendar;

public class Reservador {

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

		if (validarFecha(day, month, year)) {
			fechaInicial.set(Calendar.YEAR, year);
			fechaInicial.set(Calendar.MONTH, month - 1);
			fechaInicial.set(Calendar.DAY_OF_MONTH, day);
			if (validarHoras(fechaInicial, horaInicial, horaFinal)) {
				fechaFinal.setTime(fechaInicial.getTime());
				fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
				fechaFinal.set(Calendar.HOUR_OF_DAY, horaFinal);
				if (BBDDReservas.comprobarDisponibilidadSocio(fechaInicial, fechaFinal)
						&& BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal)) {
					BBDDReservas.hacerReserva(idInstalacion, fechaInicial, fechaFinal);
					BBDDReservas.validarPago((calcularPrecio(idInstalacion, horaInicial, horaFinal)), tipoPago);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hacerReservaAdmin(String idSala, int year, int month, int day, int horaInicial,
			int horaFinal) {
		Calendar fechaInicial = Calendar.getInstance();
		fechaInicial.set(Calendar.YEAR, year);
		fechaInicial.set(Calendar.MONTH, month - 1);
		fechaInicial.set(Calendar.DAY_OF_MONTH, day);
		fechaInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.set(Calendar.YEAR, year);
		fechaFinal.set(Calendar.MONTH, month - 1);
		fechaFinal.set(Calendar.DAY_OF_MONTH, day);
		fechaFinal.set(Calendar.HOUR_OF_DAY, horaFinal);

		int idInstalacion = BBDDReservas.buscarInstalacion(idSala);
		BBDDReservas.id = 0;

		if (BBDDReservas.comprobarDisponibilidadInstalacion(idInstalacion, fechaInicial, fechaFinal)) {
			BBDDReservas.hacerReserva(idInstalacion, fechaInicial, fechaFinal);
			return true;
		}
		return false;
	}

	private static double calcularPrecio(int id, int horaInicial, int horaFinal) {
		int duracion = horaFinal - horaInicial;
		double precio = BBDDReservas.buscarPrecioInstalacion(id);
		return precio * duracion;

	}

	private static boolean validarHoras(Calendar fecha, int horaInicial, int horaFinal) {
			return true;
	}

	private static boolean validarFecha(int day, int month, int year) {
		return true;
	}
}
