package iguReserva;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import java.awt.Font;

public class VentanaReserasPropiasUsuario extends JDialog {
	
	private static final long serialVersionUID = 6735821194521131468L;
	private JPanel pnFiltro;
	private JRadioButton rdbtnCanceladas;
	private JRadioButton rdbtnPendientes;
	private JRadioButton rdbtnRealizadas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaReserasPropiasUsuario dialog = new VentanaReserasPropiasUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaReserasPropiasUsuario() {
		setTitle("Reservas Propias");
		setBounds(100, 100, 678, 536);
		getContentPane().setLayout(null);
		getContentPane().add(getPnFiltro());
	}
	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBorder(new TitledBorder(null, "Filtro Reservas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFiltro.setBounds(10, 11, 642, 69);
			pnFiltro.setLayout(null);
			pnFiltro.add(getRdbtnCanceladas());
			pnFiltro.add(getRdbtnPendientes());
			pnFiltro.add(getRdbtnRealizadas());
		}
		return pnFiltro;
	}
	private JRadioButton getRdbtnCanceladas() {
		if (rdbtnCanceladas == null) {
			rdbtnCanceladas = new JRadioButton("Canceladas");
			rdbtnCanceladas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnCanceladas.setBounds(476, 27, 109, 23);
		}
		return rdbtnCanceladas;
	}
	private JRadioButton getRdbtnPendientes() {
		if (rdbtnPendientes == null) {
			rdbtnPendientes = new JRadioButton("Pendientes");
			rdbtnPendientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnPendientes.setBounds(250, 29, 109, 23);
		}
		return rdbtnPendientes;
	}
	private JRadioButton getRdbtnRealizadas() {
		if (rdbtnRealizadas == null) {
			rdbtnRealizadas = new JRadioButton("Realizadas");
			rdbtnRealizadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnRealizadas.setBounds(44, 27, 109, 23);
		}
		return rdbtnRealizadas;
	}
}
