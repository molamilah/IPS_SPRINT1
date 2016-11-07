package iguReserva;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import logica.BaseDatos.ExcepcionUsuarioNoEncontrado;
import reservas.Reservador;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
	private JTextField txUsuario;
	private JTextField txAño;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	private int ano = c.get(Calendar.YEAR);
	private int hora = c.get(Calendar.HOUR_OF_DAY);

	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };
	private List<Sala> salasGimnasio;
	private BaseDatos bd;

	/**
	 * Create the dialog.
	 */
	public VentanaReservaAdministracion() {
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

		cbDia.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbSalas.setSelectedIndex(0);
		txAño.setText(ano + "");
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
			pnFecha.add(getTxAño());
		}
		return pnFecha;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (rdbtnAdministracion.isSelected()) {
						cargarDiasAdministracion();
						generarHorasInicio();
						generarHorasFinAdministracion(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
					} else {
						cargarDiasUsuario();
						generarHorasInicio();
						generarHorasFinUsuario(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
					}
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
					generarHorasInicio();
					if (rdbtnAdministracion.isSelected())
						generarHorasFinAdministracion(cbInicio.getItemAt(cbInicio.getSelectedIndex()));

					else
						generarHorasFinUsuario(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
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
					if (rdbtnAdministracion.isSelected())
						generarHorasFinAdministracion(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
					else
						generarHorasFinUsuario(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
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
			btReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnAdministracion.isSelected()) {
						// Codigo hacer reservas administracion
						boolean success = false;
						success = Reservador.hacerReservaAdmin(cbSalas.getSelectedItem().toString(), ano,
								cbMes.getSelectedIndex(), Integer.parseInt(cbDia.getSelectedItem().toString()),
								Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]),
								Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0])
										+ cbFin.getSelectedIndex() + 1);
						if (!success) {
							JOptionPane.showMessageDialog(getContentPane(),
									"No se puede tramitar la reserva en el intervalo solicitado, la instalacion se encuentra "
											+ "reservada o el usuario ya posee otra reserva");
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "Su reserva ha sido realizada con exito.");
						}
					} else {
						try {
							Usuario usuario = propietarioReserva();
							boolean tipo = false;
							boolean success = false;
							if (radioButtonEfectivo.isSelected())
								tipo = true;
							success = Reservador.reservar(usuario.getId_usuario(), cbSalas.getSelectedItem().toString(),
									Integer.parseInt(txAño.getText()), cbMes.getSelectedIndex(),
									Integer.parseInt(cbDia.getSelectedItem().toString()),
									Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]),
									Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0])
											+ cbFin.getSelectedIndex() + 1,
									tipo);
							if (!success) {
								JOptionPane.showMessageDialog(getContentPane(),
										"No se puede tramitar la reserva en el intervalo solicitado, la instalacion se encuentra "
												+ "reservada o el usuario ya posee otra reserva");
							} else {
								JOptionPane.showMessageDialog(getContentPane(),
										"Su reserva ha sido realizada con exito.");
							}

						} catch (NullPointerException x) {
							JOptionPane.showMessageDialog(null, "Es obligatorio introducir el id del socio.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			btReservar.setMnemonic('R');
			btReservar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btReservar.setBounds(421, 366, 109, 23);
		}
		return btReservar;
	}

	/**
	 * Metodo que devuelve el usuario sobre el que se van a realizar las labora
	 * de cancelacion de las reservas.
	 * 
	 * @return
	 */
	private Usuario propietarioReserva() {
		try {
			if (!txUsuario.getText().equals("")) {
				if (bd.comprobarUsuario(Integer.parseInt(txUsuario.getText())))
					return bd.cargarUsuario(Integer.parseInt(txUsuario.getText()));
			}
		} catch (ExcepcionUsuarioNoEncontrado e) {
			JOptionPane.showMessageDialog(null, "El usuario no existe en la base de datos.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("Atras");
			btAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
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
			buttonGroup.add(radioButtonEfectivo);
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
			buttonGroup.add(radioButtonCuotaMensual);
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
			pnPropietarioReserva.add(getTxUsuario());
			pnPropietarioReserva.add(getRdbtnSocio());
			pnPropietarioReserva.add(getRdbtnAdministracion());
		}
		return pnPropietarioReserva;
	}

	private JRadioButton getRdbtnAdministracion() {
		if (rdbtnAdministracion == null) {
			rdbtnAdministracion = new JRadioButton("Administracion");
			buttonGroup_1.add(rdbtnAdministracion);
			rdbtnAdministracion.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					habilitarReservaAdministracion();
				}
			});
			rdbtnAdministracion.setSelected(true);
			rdbtnAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnAdministracion.setBounds(56, 29, 131, 23);
		}
		return rdbtnAdministracion;
	}

	private JRadioButton getRdbtnSocio() {
		if (rdbtnSocio == null) {
			rdbtnSocio = new JRadioButton("Socio");
			buttonGroup_1.add(rdbtnSocio);
			rdbtnSocio.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					habilitarReservaSocio();
				}
			});
			rdbtnSocio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnSocio.setBounds(56, 72, 109, 23);
		}
		return rdbtnSocio;
	}

	private void cargarMesesUsuario() {
		if (dia > 15) {
			String aux[] = { meses[mes], meses[mes + 1] };
			cbMes.setModel(new DefaultComboBoxModel<String>(aux));
		} else {
			String aux[] = { meses[mes] };
			cbMes.setModel(new DefaultComboBoxModel<String>(aux));
		}
	}

	private void cargarMesesAdministracion() {
		int diferencia = meses.length - mes;
		String[] aux = new String[diferencia];
		int j = 0;
		for (int i = mes; i < meses.length; i++) {
			aux[j] = meses[i];
			j++;
		}
		cbMes.setModel(new DefaultComboBoxModel<String>(aux));
	}

	private void generarHorasFinUsuario(String horaInicio) {
		if (horaInicio.equals("23:00")) {
			String[] horas = new String[] { "23:59" };
			cbFin.setModel(new DefaultComboBoxModel<String>(horas));
		} else {
			String[] horas = new String[2];
			int hora = Integer.parseInt(horaInicio.split(":")[0]);
			for (int i = 0; i < 2; i++) {
				if (hora < 10) {
					horas[i] = "0" + hora + ":59";
					hora++;
				} else {
					horas[i] = hora + ":59";
					hora++;
				}
			}
			cbFin.setModel(new DefaultComboBoxModel<String>(horas));
		}
	}

	private void generarHorasFinAdministracion(String horaInicio) {
		if (horaInicio.equals("23:00")) {
			String[] horas = new String[] { "23:59" };
			cbFin.setModel(new DefaultComboBoxModel<String>(horas));
		} else {
			int horaI = Integer.parseInt(horaInicio.split(":")[0]);
			int diferencia = 24 - horaI;
			String[] horas = new String[diferencia];
			for (int i = 0; i < horas.length; i++) {
				if (horaI < 10) {
					horas[i] = "0" + horaI + ":59";
					horaI++;
				} else {
					horas[i] = horaI + ":59";
					horaI++;
				}
			}
			cbFin.setModel(new DefaultComboBoxModel<String>(horas));
		}
	}

	private void generarHorasInicio() {
		int aux = Integer.parseInt(cbDia.getSelectedItem().toString());
		if (dia == aux) {
			int diferencia = 24 - hora;
			String[] horas = new String[diferencia - 1];
			int j = hora + 1;
			for (int i = 0; i < diferencia - 1; i++) {
				if (j < 10) {
					horas[i] = "0" + j + ":00";
					j++;
				} else {
					horas[i] = j + ":00";
					j++;
				}
			}
			cbInicio.setModel(new DefaultComboBoxModel<String>(horas));
		} else {
			String[] horas = new String[24];
			for (int i = 0; i < 24; i++) {
				if (i < 10) {
					horas[i] = "0" + i + ":00";
				} else {
					horas[i] = i + ":00";
				}
			}
			cbInicio.setModel(new DefaultComboBoxModel<String>(horas));
		}
	}

	private void habilitarReservaSocio() {
		cargarMesesUsuario();
		cargarDiasUsuario();

		habilitarPanelMedioPago();
		txUsuario.setEnabled(true);

		cbDia.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbSalas.setSelectedIndex(0);
	}

	private void habilitarReservaAdministracion() {
		cargarMesesAdministracion();
		cargarDiasAdministracion();

		deshabilitarMedioPago();
		txUsuario.setEnabled(false);

		cbDia.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbSalas.setSelectedIndex(0);
	}

	private void deshabilitarMedioPago() {
		pnFormaPago.setEnabled(false);
		radioButtonCuotaMensual.setEnabled(false);
		radioButtonEfectivo.setEnabled(false);
	}

	private void habilitarPanelMedioPago() {
		pnFormaPago.setEnabled(true);
		radioButtonCuotaMensual.setEnabled(true);
		radioButtonEfectivo.setEnabled(true);
	}

	private void cargarDiasAdministracion() {
		String[] diasMes = calcularDiasMes(cbMes.getItemAt(0));
		int diferencia = Integer.parseInt(diasMes[diasMes.length - 1]) - (dia) + 1;
		String[] dias = new String[diferencia];
		int j = 0;
		for (int i = dia; i <= diasMes.length; i++) {
			dias[j] = i + "";
			j++;
		}
		cbDia.setModel(new DefaultComboBoxModel<String>(dias));
	}

	/**
	 * Metodo para calcular el numero de dias que tiene un mes.
	 * 
	 * @return numero de dias por mes.
	 */
	private String[] calcularDiasMes(String mes) {
		String[] dias;
		String aux = mes;
		if (aux.equals("Enero") || aux.equals("Marzo") || aux.equals("Mayo") || aux.equals("Julio")
				|| aux.equals("Agosto") || aux.equals("Octubre") || aux.equals("Diciembre")) {
			dias = new String[31];
			for (int i = 0; i < 31; i++)
				dias[i] = "" + (i + 1);
		} else if (aux.equals("Febrero")) {
			dias = new String[28];
			for (int i = 0; i < 28; i++)
				dias[i] = "" + (i + 1);
		} else {
			dias = new String[30];
			for (int i = 0; i < 30; i++)
				dias[i] = "" + (i + 1);
		}
		return dias;
	}

	private void cargarDiasUsuario() {
		if (cbMes.getSelectedIndex() == 0) {
			String[] diasMes = calcularDiasMes(cbMes.getItemAt(0));
			int diferencia = Integer.parseInt(diasMes[diasMes.length - 1]) - (dia) + 1;
			if (diferencia < 15) {
				String[] dias = new String[diferencia];
				int j = 0;
				for (int i = dia; i <= diasMes.length; i++) {
					dias[j] = i + "";
					j++;
				}
				cbDia.setModel(new DefaultComboBoxModel<String>(dias));
			} else {
				String[] dias = new String[15];
				int j = dia;
				for (int i = 0; i < 15; i++) {
					dias[i] = j + "";
					j++;
				}
				cbDia.setModel(new DefaultComboBoxModel<String>(dias));
			}
		} else {
			String[] diasMes = calcularDiasMes(cbMes.getItemAt(0));
			int diferencia = Integer.parseInt(diasMes[diasMes.length - 1]) - (dia);
			String[] dias = new String[15 - diferencia];
			int j = 0;
			for (int i = 1; i <= dias.length; i++) {
				dias[j] = i + "";
				j++;
			}
			cbDia.setModel(new DefaultComboBoxModel<String>(dias));
		}
	}

	private JTextField getTxUsuario() {
		if (txUsuario == null) {
			txUsuario = new JTextField();
			txUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txUsuario.setBounds(29, 114, 171, 33);
			txUsuario.setColumns(10);
		}
		return txUsuario;
	}

	private JTextField getTxAño() {
		if (txAño == null) {
			txAño = new JTextField();
			txAño.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txAño.setEnabled(false);
			txAño.setEditable(false);
			txAño.setBounds(206, 32, 86, 26);
			txAño.setColumns(10);
		}
		return txAño;
	}
}