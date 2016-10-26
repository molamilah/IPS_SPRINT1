package ocupacion;

public class OcupacionSalas {

	public static boolean indicarLlegada(String DNI) {
		return BBDDOcupacion.Llegada(DNI);
	}

	public static boolean indicarSalida(String DNI) {
		return BBDDOcupacion.Salida(DNI);
	}
}
