package iguConflictos;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import reservas.BBDDReservas;

public class VentanaConflictos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar fechaConflicto;
	private Calendar fechaConflictoFin;
	private ArrayList<Integer> reservasConflictivas;
	private JButton btnUpdate;
	private JButton btnCancel;
	private JTextArea txtInfo;
	private int idInstalacion;

	/**
	 * Create the frame.
	 */
	public VentanaConflictos(Calendar fechaConflicto, int horaInicio, int horaFin, int idInstalacion) {
		this.fechaConflicto = (Calendar) fechaConflicto.clone();
		this.fechaConflictoFin = (Calendar) fechaConflicto.clone();
		this.fechaConflicto.set(Calendar.HOUR_OF_DAY, horaInicio);
		this.fechaConflictoFin.set(Calendar.HOUR_OF_DAY, horaFin);
		reservasConflictivas = new ArrayList<Integer>();
		this.idInstalacion = idInstalacion;
		setTitle("Conflictos Encontrados");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaConflictos.class.getResource("/img/img-recepcion-reducida.jpg")));
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnUpdate());
		getContentPane().add(getBtnCancel());
		getContentPane().add(getTxtInfo());
		reservasConflictivas();

	}

	private void reservasConflictivas() {
		reservasConflictivas = BBDDReservas.buscarConflicto(idInstalacion, fechaConflicto, fechaConflictoFin);
	}

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("Reservar");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					BBDDReservas.resolverConflictos(reservasConflictivas, fechaConflicto, fechaConflictoFin,
							idInstalacion);
					dispose();
				}
			});
			btnUpdate.setBounds(75, 217, 101, 23);
		}
		return btnUpdate;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancelar");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancel.setBounds(295, 217, 89, 23);
		}
		return btnCancel;
	}

	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setLineWrap(true);
			txtInfo.setWrapStyleWord(true);
			txtInfo.setText("Tu reserva con la fecha " + fechaConflicto.get(Calendar.DAY_OF_MONTH) + " de "
					+ fechaConflicto.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " de " + fechaConflicto.get(Calendar.YEAR) + " tiene un conflicto con 1 o mas reservas de existentes."
							+ " Debes decidir si anular dichas reservas para realizar la tuya o cancelar tu intento de reserva\n"
							+ "\u002AEn caso de que decidas anular las existentes se notificara tal cambio a los afectados");
			txtInfo.setEditable(false);
			txtInfo.setBounds(55, 28, 329, 174);
		}
		return txtInfo;
	}
}
