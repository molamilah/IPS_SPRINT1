package reservas;

import java.util.Calendar;
import java.util.Date;

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

	private static double calcularPrecio(int id, int horaInicial, int horaFinal) {
		int duracion = horaFinal - horaInicial;
		double precio = BBDDReservas.buscarPrecioInstalacion(id);
		return precio * duracion;

	}

	private static boolean validarHoras(Calendar fecha, int horaInicial, int horaFinal) {
//		if (!validarHoraInicial(horaInicial) || !validarHoraFinal(horaFinal))
//			return false;
//		Calendar c1 = (Calendar) fecha.clone();
//		Calendar c2 = (Calendar) fecha.clone();
//		Calendar actual = Calendar.getInstance();
//		c1.set(Calendar.HOUR_OF_DAY, horaInicial);
//		c2.set(Calendar.HOUR_OF_DAY, horaFinal);
//		if (horaInicial == 23){
//			c2.add(Calendar.DAY_OF_MONTH, +1);
//			c2.set(Calendar.HOUR_OF_DAY, 0);
//		}
//		long duracion = c2.getTimeInMillis() - c1.getTimeInMillis();
//		long maximo = 7200000;
//		if (duracion > maximo || duracion <= 0 || c1.getTimeInMillis() - actual.getTimeInMillis() <= 3600000) {
//			return false;
//		} else
			return true;
	}

	private static boolean validarHoraInicial(int horaInicial) {
		if (horaInicial < 0 || horaInicial > 23) {
			return false;
		} else
			return true;
	}

	private static boolean validarHoraFinal(int horaFinal) {
		if (horaFinal < 0 || horaFinal > 25) {
			return false;
		} else
			return true;
	}

	private static boolean validarFecha(int day, int month, int year) {
//		Calendar reserva = Calendar.getInstance();
//		Calendar actual = Calendar.getInstance();
//		Calendar maximo = Calendar.getInstance();
//		maximo.add(Calendar.DAY_OF_MONTH, +15);
//		if (!validarAnno(year) || !validarMes(year, month) || !validarDia(month, year, day))
//			return false;
//		else {
//			reserva.set(Calendar.YEAR, year);
//			reserva.set(Calendar.MONTH, month - 1);
//			reserva.set(Calendar.DAY_OF_MONTH, day);
//			if (reserva.before(actual) || reserva.after(maximo))
//				return false;
//		}

		return true;
	}

	private static boolean validarDia(int month, int anno, int day) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.MONTH, month - 1);
		c1.set(Calendar.YEAR, anno);
		int maximo = c1.getMaximum(Calendar.DAY_OF_MONTH);
		if (day > maximo || day < 1) {
			return false;
		}
		return true;
	}

	private static boolean validarAnno(int anno) {
		Calendar c1 = Calendar.getInstance();
		int annoActual = c1.get(Calendar.YEAR);
		if (anno < annoActual)
			return false;
		return true;
	}

	private static boolean validarMes(int anno, int month) {
		Calendar reserva = Calendar.getInstance();
		reserva.set(Calendar.YEAR, anno);
		Calendar baja = comprobarBaja();
		if (month - 1 < 0 || month - 1 > 11)
			return false;
		if (baja != null) {
			reserva.set(Calendar.MONTH, month - 1);
			if ((reserva.after(baja) || reserva.getTime() == baja.getTime())) {
				return false;
			}
		}
		return true;
	}

	private static Calendar comprobarBaja() {
		Calendar c1 = null;
		Date fechaBaja = BBDDReservas.comprobarBaja();
		if (fechaBaja != null) {
			c1 = Calendar.getInstance();
			c1.setTime(fechaBaja);
		}
		return c1;
	}
}
