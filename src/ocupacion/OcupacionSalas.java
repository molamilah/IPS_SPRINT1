package ocupacion;

public class OcupacionSalas {

	public static boolean indicarLlegada(int idSocio, int idInstalacion) {
		return BBDDOcupacion.Llegada(idSocio, idInstalacion);
	}

	public static boolean indicarSalida(int idSocio, int idInstalacion) {
		return BBDDOcupacion.Salida(idSocio, idInstalacion);
	}

}
