package conflictos;

import javax.swing.JDialog;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JTextArea;

import logica.Reserva;

public class VentanaConflictos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Reserva> reservasConflictivas;

	/**
	 * Create the frame.
	 */
	public VentanaConflictos(ArrayList<Reserva> reservasConflictivas) {
		this.reservasConflictivas = reservasConflictivas;
		setTitle("Conflictos Encontrados");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaConflictos.class.getResource("/img/img-recepcion-reducida.jpg")));
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

	}
}
