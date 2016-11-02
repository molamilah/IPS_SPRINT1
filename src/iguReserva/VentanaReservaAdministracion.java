package iguReserva;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.util.Calendar;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import logica.BaseDatos;
import logica.Sala;
import logica.Usuario;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaReservaAdministracion extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbReservaInstalaciones;
	private JLabel lbSalas;
	private JTextField txPrecio;
	private JComboBox<String> cbSalas;
	private JLabel lbPrecio;
	private JPanel pnFecha;
	private JComboBox<String> cbMes;
	private JLabel lbMes;
	private JLabel lbDia;
	private JComboBox<String> cbDia;
	private JLabel lbAno;
	private JPanel pnHorario;
	private JLabel label_6;
	private JComboBox<String> cbInicio;
	private JLabel lbFin;
	private JComboBox<String> cbFin;
	private JButton btReservar;
	private JButton btAtras;
	private JPanel pnFormaPago;
	private JRadioButton radioButtonEfectivo;
	private JRadioButton radioButtonCuotaMensual;
	private JPanel pnPropietarioReserva;
	private JRadioButton rdbtnAdministracion;
	private JRadioButton rdbtnSocio;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JComboBox<String> cbAno;
	
	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	private int ano = c.get(Calendar.YEAR);
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int min = c.get(Calendar.MINUTE);

	private Usuario usuario;
	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };
	private List<Sala> salasGimnasio;
	private BaseDatos bd;

	/**
	 * Create the dialog.
	 */
	public VentanaReservaAdministracion(Usuario user) {
		this.usuario = user;
		bd = new BaseDatos();
		salasGimnasio = bd.cargarSalas();
		setTitle("Reserva Instalaciones Administracion");
		setBounds(100, 100, 556, 438);
		getContentPane().setLayout(null);
		getContentPane().add(getLbReservaInstalaciones());
		getContentPane().add(getLbSalas());
		getContentPane().add(getTxPrecio());
		getContentPane().add(getCbSalas());
		getContentPane().add(getLbPrecio());
		getContentPane().add(getPnFecha());
		getContentPane().add(getPnHorario());
		getContentPane().add(getBtReservar());
		getContentPane().add(getBtAtras());
		getContentPane().add(getPnFormaPago());
		getContentPane().add(getPnPropietarioReserva());
	}

	private JLabel getLbReservaInstalaciones() {
		if (lbReservaInstalaciones == null) {
			lbReservaInstalaciones = new JLabel("Reserva de instalaciones");
			lbReservaInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lbReservaInstalaciones.setBounds(163, 24, 199, 38);
		}
		return lbReservaInstalaciones;
	}

	private JLabel getLbSalas() {
		if (lbSalas == null) {
			lbSalas = new JLabel("Salas:");
			lbSalas.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbSalas.setDisplayedMnemonic('S');
			lbSalas.setBounds(10, 86, 52, 20);
		}
		return lbSalas;
	}

	private JTextField getTxPrecio() {
		if (txPrecio == null) {
			txPrecio = new JTextField();
			txPrecio.setText("0.0\u20AC");
			txPrecio.setHorizontalAlignment(SwingConstants.CENTER);
			txPrecio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txPrecio.setEditable(false);
			txPrecio.setColumns(10);
			txPrecio.setBounds(149, 119, 86, 30);
		}
		return txPrecio;
	}

	private JComboBox<String> getCbSalas() {
		if (cbSalas == null) {
			cbSalas = new JComboBox<String>();
			cbSalas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ponerPrecio();
				}
			});
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbSalas.setBounds(10, 120, 127, 28);
			int tamaño = salasGimnasio.size();
			String[] salas = new String[tamaño];
			for (int i = 0; i < salasGimnasio.size(); i++)
				salas[i] = salasGimnasio.get(i).getDescripcion();
			cbSalas.setModel(new DefaultComboBoxModel<String>(salas));
			cbSalas.setSelectedIndex(0);
			ponerPrecio();
		}
		return cbSalas;
	}

	private void ponerPrecio() {
		txPrecio.setText(salasGimnasio.get(cbSalas.getSelectedIndex()).getPrecio() + "€");
	}

	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio");
			lbPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbPrecio.setBounds(150, 88, 46, 14);
		}
		return lbPrecio;
	}

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setLayout(null);
			pnFecha.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFecha.setBounds(235, 83, 308, 70);
			pnFecha.add(getCbMes());
			pnFecha.add(getLbMes());
			pnFecha.add(getLbDia());
			pnFecha.add(getCbDia());
			pnFecha.add(getLbAno());
			pnFecha.add(getCbAno());
		}
		return pnFecha;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbMes.setBounds(80, 33, 116, 26);
			cbMes.setModel(new DefaultComboBoxModel<String>(meses));
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

	private JComboBox<String> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<String>();
			cbDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbDia.setBounds(10, 33, 60, 26);
		}
		return cbDia;
	}

	private JLabel getLbAno() {
		if (lbAno == null) {
			lbAno = new JLabel("A\u00F1o:");
			lbAno.setLabelFor(getCbAno());
			lbAno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbAno.setBounds(206, 12, 46, 19);
		}
		return lbAno;
	}

	private JPanel getPnHorario() {
		if (pnHorario == null) {
			pnHorario = new JPanel();
			pnHorario.setLayout(null);
			pnHorario.setBorder(new TitledBorder(null, "Horario( Max 2 horas)", TitledBorder.LEADING, TitledBorder.TOP,

					null, null));
			pnHorario.setBounds(241, 169, 212, 82);
			pnHorario.add(getLabel_6());
			pnHorario.add(getCbInicio());
			pnHorario.add(getLbFin());
			pnHorario.add(getCbFin());
		}
		return pnHorario;
	}

	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel("Inicio:");
			label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
			label_6.setDisplayedMnemonic('I');
			label_6.setBounds(10, 11, 89, 26);
		}
		return label_6;
	}

	private JComboBox<String> getCbInicio() {
		if (cbInicio == null) {
			cbInicio = new JComboBox<String>();
			cbInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			cbInicio.setBounds(10, 42, 89, 26);
		}
		return cbInicio;
	}

	private JLabel getLbFin() {
		if (lbFin == null) {
			lbFin = new JLabel("Fin:");
			lbFin.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbFin.setDisplayedMnemonic('F');
			lbFin.setBounds(109, 11, 89, 26);
		}
		return lbFin;
	}

	private JComboBox<String> getCbFin() {
		if (cbFin == null) {
			cbFin = new JComboBox<String>();
			cbFin.setBounds(109, 42, 89, 26);
		}
		return cbFin;
	}

	private JButton getBtReservar() {
		if (btReservar == null) {
			btReservar = new JButton("Reservar");
			btReservar.setMnemonic('R');
			btReservar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btReservar.setBounds(421, 366, 109, 23);
		}
		return btReservar;
	}

	private JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("Atras");
			btAtras.setMnemonic('A');
			btAtras.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btAtras.setBounds(322, 366, 89, 23);
		}
		return btAtras;
	}

	private JPanel getPnFormaPago() {
		if (pnFormaPago == null) {
			pnFormaPago = new JPanel();
			pnFormaPago.setLayout(null);
			pnFormaPago.setBorder(
					new TitledBorder(null, "Forma de pago", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFormaPago.setBounds(240, 264, 225, 68);
			pnFormaPago.add(getRadioButtonEfectivo());
			pnFormaPago.add(getRadioButtonCuotaMensual());
		}
		return pnFormaPago;
	}

	private JRadioButton getRadioButtonEfectivo() {
		if (radioButtonEfectivo == null) {
			radioButtonEfectivo = new JRadioButton("Efectivo");
			radioButtonEfectivo.setEnabled(false);
			radioButtonEfectivo.setToolTipText("Seleccione si desea pagar en el momento de usar la instalac\u00EDon.");
			radioButtonEfectivo.setMnemonic('E');
			radioButtonEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			radioButtonEfectivo.setBounds(6, 28, 89, 23);
		}
		return radioButtonEfectivo;
	}

	private JRadioButton getRadioButtonCuotaMensual() {
		if (radioButtonCuotaMensual == null) {
			radioButtonCuotaMensual = new JRadioButton("Cuota Mensual");
			radioButtonCuotaMensual.setEnabled(false);
			radioButtonCuotaMensual.setToolTipText("Selecciona Para que se a\u00F1ada el pago a su cuota mensual.");
			radioButtonCuotaMensual.setSelected(true);
			radioButtonCuotaMensual.setFont(new Font("Tahoma", Font.PLAIN, 15));
			radioButtonCuotaMensual.setBounds(97, 28, 121, 23);
		}
		return radioButtonCuotaMensual;
	}

	private JPanel getPnPropietarioReserva() {
		if (pnPropietarioReserva == null) {
			pnPropietarioReserva = new JPanel();
			pnPropietarioReserva.setBorder(
					new TitledBorder(null, "Propietario Reserva", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnPropietarioReserva.setBounds(10, 169, 225, 158);
			pnPropietarioReserva.setLayout(null);
			pnPropietarioReserva.add(getRdbtnAdministracion());
			pnPropietarioReserva.add(getRdbtnSocio());
			pnPropietarioReserva.add(getTextField());
		}
		return pnPropietarioReserva;
	}

	private JRadioButton getRdbtnAdministracion() {
		if (rdbtnAdministracion == null) {
			rdbtnAdministracion = new JRadioButton("Administracion");
			rdbtnAdministracion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			buttonGroup.add(rdbtnAdministracion);
			rdbtnAdministracion.setSelected(true);
			rdbtnAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnAdministracion.setBounds(56, 29, 131, 23);
		}
		return rdbtnAdministracion;
	}

	private JRadioButton getRdbtnSocio() {
		if (rdbtnSocio == null) {
			rdbtnSocio = new JRadioButton("Socio");
			rdbtnSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			buttonGroup.add(rdbtnSocio);
			rdbtnSocio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnSocio.setBounds(56, 72, 109, 23);
		}
		return rdbtnSocio;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
			textField.setEnabled(false);
			textField.setBounds(29, 114, 171, 33);
			textField.setColumns(10);
		}
		return textField;
	}
	private JComboBox<String> getCbAno() {
		if (cbAno == null) {
			cbAno = new JComboBox<String>();
			cbAno.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbAno.setBounds(206, 33, 92, 25);
			String[] anos = new String[5];
			int j = this.ano;
			for(int i = 0;i<anos.length;i++){
				anos[i] = j+"";
				j++;
			}
			cbAno.setModel(new DefaultComboBoxModel<String>(anos));
		}
		return cbAno;
	}
}