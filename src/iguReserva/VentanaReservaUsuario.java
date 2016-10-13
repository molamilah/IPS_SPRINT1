package iguReserva;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaReservaUsuario extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbReservas;
	private JLabel lbSalas;
	private JComboBox<String> cbSalas;
	private JLabel lbPrecio;
	private JTextField txPrecio;
	private JPanel pnFecha;
	private JComboBox<String> cbMes;
	private JLabel lbMes;
	private JLabel lbDia;
	private JComboBox<String> cbDia;
	private JLabel lbAno;
	private JTextField txAno;
	private JPanel pnHorario;
	private JLabel lbInicio;
	private JComboBox<String> cbInicio;
	private JLabel lbFin;
	private JComboBox<String> cbFin;
	private JButton btReserva;
	private JButton btAtras;

	/**
	 * Create the dialog.
	 */
	public VentanaReservaUsuario() {
		getContentPane().setLayout(null);
		getContentPane().add(getLbReservas());
		getContentPane().add(getLbSalas());
		getContentPane().add(getCbSalas());
		getContentPane().add(getLbPrecio());
		getContentPane().add(getTxPrecio());
		getContentPane().add(getPnFecha());
		getContentPane().add(getPnHorario());
		getContentPane().add(getBtReserva());
		getContentPane().add(getBtAtras());
		
	}
	private JLabel getLbReservas() {
		if (lbReservas == null) {
			lbReservas = new JLabel("Reserva de instalaciones");
			lbReservas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lbReservas.setBounds(146, 30, 199, 38);
		}
		return lbReservas;
	}
	private JLabel getLbSalas() {
		if (lbSalas == null) {
			lbSalas = new JLabel("Salas:");
			lbSalas.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbSalas.setDisplayedMnemonic('S');
			lbSalas.setBounds(10, 95, 52, 20);
		}
		return lbSalas;
	}
	private JComboBox getCbSalas() {
		if (cbSalas == null) {
			cbSalas = new JComboBox();
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbSalas.setBounds(10, 123, 97, 28);
		}
		return cbSalas;
	}
	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio");
			lbPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbPrecio.setBounds(122, 99, 46, 14);
		}
		return lbPrecio;
	}
	private JTextField getTxPrecio() {
		if (txPrecio == null) {
			txPrecio = new JTextField();
			txPrecio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txPrecio.setEditable(false);
			txPrecio.setColumns(10);
			txPrecio.setBounds(122, 124, 86, 28);
		}
		return txPrecio;
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setLayout(null);
			pnFecha.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFecha.setBounds(228, 92, 308, 70);
			pnFecha.add(getCbMes());
			pnFecha.add(getLbMes());
			pnFecha.add(getLbDia());
			pnFecha.add(getCbDia());
			pnFecha.add(getLbAno());
			pnFecha.add(getTxAno());
		}
		return pnFecha;
	}
	private JComboBox getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox();
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbMes.setBounds(80, 33, 116, 26);
		}
		return cbMes;
	}
	private JLabel getLbMes() {
		if (lbMes == null) {
			lbMes = new JLabel("Mes:");
			lbMes.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbMes.setDisplayedMnemonic('M');
			lbMes.setBounds(80, 11, 53, 20);
		}
		return lbMes;
	}
	private JLabel getLbDia() {
		if (lbDia == null) {
			lbDia = new JLabel("D\u00EDa:");
			lbDia.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbDia.setDisplayedMnemonic('D');
			lbDia.setBounds(10, 16, 46, 15);
		}
		return lbDia;
	}
	private JComboBox getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox();
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbDia.setBounds(10, 33, 60, 26);
		}
		return cbDia;
	}
	private JLabel getLbAno() {
		if (lbAno == null) {
			lbAno = new JLabel("A\u00F1o");
			lbAno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbAno.setBounds(206, 12, 46, 19);
		}
		return lbAno;
	}
	private JTextField getTxAno() {
		if (txAno == null) {
			txAno = new JTextField();
			txAno.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txAno.setEditable(false);
			txAno.setColumns(10);
			txAno.setBounds(206, 33, 86, 26);
		}
		return txAno;
	}
	private JPanel getPnHorario() {
		if (pnHorario == null) {
			pnHorario = new JPanel();
			pnHorario.setLayout(null);
			pnHorario.setBorder(new TitledBorder(null, "Horario( Max 2 horas)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHorario.setBounds(10, 181, 212, 82);
			pnHorario.add(getLbInicio());
			pnHorario.add(getCbInicio());
			pnHorario.add(getLbFin());
			pnHorario.add(getCbFin());
		}
		return pnHorario;
	}
	private JLabel getLbInicio() {
		if (lbInicio == null) {
			lbInicio = new JLabel("Inicio:");
			lbInicio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbInicio.setDisplayedMnemonic('I');
			lbInicio.setBounds(10, 23, 89, 14);
		}
		return lbInicio;
	}
	private JComboBox getCbInicio() {
		if (cbInicio == null) {
			cbInicio = new JComboBox();
			cbInicio.setBounds(10, 42, 89, 26);
		}
		return cbInicio;
	}
	private JLabel getLbFin() {
		if (lbFin == null) {
			lbFin = new JLabel("Fin:");
			lbFin.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbFin.setDisplayedMnemonic('F');
			lbFin.setBounds(109, 23, 89, 14);
		}
		return lbFin;
	}
	private JComboBox getCbFin() {
		if (cbFin == null) {
			cbFin = new JComboBox();
			cbFin.setBounds(109, 42, 89, 26);
		}
		return cbFin;
	}
	private JButton getBtReserva() {
		if (btReserva == null) {
			btReserva = new JButton("Reservar");
			btReserva.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btReserva.setBounds(412, 325, 109, 23);
		}
		return btReserva;
	}
	private JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("Atras");
			btAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btAtras.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btAtras.setBounds(318, 325, 89, 23);
		}
		return btAtras;
	}
}
