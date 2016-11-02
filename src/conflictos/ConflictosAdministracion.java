package conflictos;

import java.util.ArrayList;

import reservas.BBDDReservas;

public class ConflictosAdministracion {

	public static boolean conflictoReservaUnica(ArrayList<Integer> idReservas) {
		String datos ="";
		for (int id : idReservas) {
			datos = BBDDReservas.buscarDatosReserva(id);
			VentanaConflictos vc = new VentanaConflictos(datos);
			
			
			
			
		}
	}
}
