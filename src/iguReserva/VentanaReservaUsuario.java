package iguReserva;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import logica.BaseDatos;
import logica.Sala;
import logica.Usuario;
import reservas.Reservador;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class VentanaReservaUsuario extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbReservas;
	private JLabel lbSalas;
	private JComboBox<String> cbSalas;
	private JLabel lbPrecio;
	private JTextField txtPrecio;
	private JPanel pnFecha;
	private JComboBox<String> cbMes;
	private JLabel lbMes;
	private JLabel lbDia;
	private JComboBox<String> cbDia;
	private JLabel lbAno;
	private JPanel pnHorario;
	private JLabel lbInicio;
	private JComboBox<String> cbInicio;
	private JLabel lbFin;
	private JComboBox<String> cbFin;
	private JButton btReserva;
	private JButton btAtras;
	private JTextField txtDisponibilidad;
	private JLabel lblDisponibilidad;
	private JTextField txAno;

	private Usuario usuario;
	private BaseDatos bd;
	private List<Sala> salasGimnasio;

	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	//private int año = c.get(Calendar.YEAR) - 1900;
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int min = c.get(Calendar.MINUTE);

	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };
	private JPanel pnMetodoPago;
	private JRadioButton rdbtnEfectivo;
	private JRadioButton rdbtnCuota;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public VentanaReservaUsuario(Usuario usuario) {
		setResizable(false);
		bd = new BaseDatos();
		salasGimnasio = bd.cargarSalas();
		this.usuario = usuario;
		setBounds(100, 100, 533, 383);
		setTitle("Reserva Instalaciones");
		getContentPane().setLayout(null);
		getContentPane().add(getLbReservas());
		getContentPane().add(getLbSalas());
		getContentPane().add(getTxPrecio());
		getContentPane().add(getCbSalas());
		getContentPane().add(getLbPrecio());
		getContentPane().add(getPnFecha());
		getContentPane().add(getPnHorario());
		getContentPane().add(getBtReserva());
		getContentPane().add(getBtAtras());
		getContentPane().add(getTxtDisponibilidad());
		getContentPane().add(getLblDisponibilidad());

		cbDia.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbSalas.setSelectedIndex(0);
		getContentPane().add(getPnMetodoPago());

	}

	private JTextField getTxAno() {
		if (txAno == null) {
			txAno = new JTextField();
			txAno.setHorizontalAlignment(SwingConstants.CENTER);
			txAno.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txAno.setEditable(false);
			txAno.setColumns(10);
			txAno.setBounds(206, 33, 86, 26);
		}
		return txAno;
	}

	private JLabel getLbReservas() {
		if (lbReservas == null) {
			lbReservas = new JLabel("Reserva de instalaciones");
			lbReservas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lbReservas.setBounds(169, 30, 199, 38);
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

	private JComboBox<String> getCbSalas() {
		if (cbSalas == null) {
			cbSalas = new JComboBox<String>();
			cbSalas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ponerPrecio();
				}
			});
			int tamaño = salasGimnasio.size();
			String[] salas = new String[tamaño];
			for (int i = 0; i < salasGimnasio.size(); i++)
				salas[i] = salasGimnasio.get(i).getDescripcion();
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbSalas.setBounds(10, 123, 97, 28);
			cbSalas.setModel(new DefaultComboBoxModel<String>(salas));
			ponerPrecio();
		}
		return cbSalas;
	}

	private void ponerPrecio() {
		txtPrecio.setText(salasGimnasio.get(cbSalas.getSelectedIndex()).getPrecio() + "ï¿½");
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
		if (txtPrecio == null) {
			txtPrecio = new JTextField();
			txtPrecio.setHorizontalAlignment(SwingConstants.CENTER);
			txtPrecio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPrecio.setEditable(false);
			txtPrecio.setColumns(10);
			txtPrecio.setBounds(122, 124, 86, 28);
		}
		return txtPrecio;
	}

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setLayout(null);
			pnFecha.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFecha.setBounds(218, 95, 308, 70);
			pnFecha.add(getCbMes());
			pnFecha.add(getLbMes());
			pnFecha.add(getLbDia());
			pnFecha.add(getCbDia());
			pnFecha.add(getLbAno());
			pnFecha.add(getTxAno());
		}
		return pnFecha;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cargarDias();
					generarHorasInicio();
					generarHorasFin(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
				}
			});
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbMes.setBounds(80, 33, 116, 26);
			if (dia > 15) {
				String aux[] = { meses[mes], meses[mes + 1] };
				cbMes.setModel(new DefaultComboBoxModel<String>(aux));
			} else {
				String aux[] = { meses[mes] };
				cbMes.setModel(new DefaultComboBoxModel<String>(aux));
			}
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

	private void cargarDias() {
		if (cbMes.getSelectedIndex() == 0) {
			String[] diasMes = calcularDiasMes(cbMes.getItemAt(0));
			int diferencia = Integer.parseInt(diasMes[diasMes.length - 1]) - (dia);
			if (diferencia < 15) {
				String[] dias = new String[diferencia];
				int j = 0;
				for (int i = dia; i < diasMes.length; i++) {
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

	private JComboBox<String> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<String>();
			cbDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarHorasInicio();
					generarHorasFin(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
				}
			});
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbDia.setBounds(10, 33, 60, 26);
			cargarDias();
		}
		return cbDia;
	}

	private JLabel getLbAno() {
		if (lbAno == null) {
			lbAno = new JLabel("A\u00F1o:");
			lbAno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbAno.setBounds(216, 12, 46, 19);
		}
		return lbAno;
	}

	private JPanel getPnHorario() {
		if (pnHorario == null) {
			pnHorario = new JPanel();
			pnHorario.setLayout(null);
			pnHorario.setBorder(new TitledBorder(null, "Horario( Max 2 horas)", TitledBorder.LEADING, TitledBorder.TOP,
					null, null));
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
			lbInicio.setLabelFor(getCbInicio());
			lbInicio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbInicio.setDisplayedMnemonic('I');
			lbInicio.setBounds(10, 11, 89, 26);
		}
		return lbInicio;
	}

	private JComboBox<String> getCbInicio() {
		if (cbInicio == null) {
			cbInicio = new JComboBox<String>();
			cbInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarHorasFin(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
				}
			});
			cbInicio.setBounds(10, 42, 89, 26);
			generarHorasInicio();
		}
		return cbInicio;
	}

	private JLabel getLbFin() {
		if (lbFin == null) {
			lbFin = new JLabel("Fin:");
			lbFin.setLabelFor(getCbFin());
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

	private JButton getBtReserva() {
		if (btReserva == null) {
			btReserva = new JButton("Reservar");
			btReserva.setEnabled(true);
			btReserva.setMnemonic('R');
			btReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean tipo = false;
					boolean success = false;
					if (rdbtnEfectivo.isSelected())
						tipo = true;
					success = Reservador.reservar(usuario.getId_usuario(), cbSalas.getSelectedItem().toString(),
							Integer.parseInt(txAno.getText()), cbMes.getSelectedIndex(),
							Integer.parseInt(cbDia.getSelectedItem().toString()),
							Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]),
							Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0])
									+ cbFin.getSelectedIndex() + 1,
							tipo);
					if (!success) {
						JOptionPane.showMessageDialog(getContentPane(),
								"No se puede tramitar la reserva en el intervalo solicitado, la instalacion se encuentra "
										+ "reservada o el usuario ya posee otra reserva");
						txtDisponibilidad.setBackground(Color.RED);
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Reserva realizada con exito");
						txtDisponibilidad.setBackground(Color.GREEN);
					}

				}
			});
			btReserva.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btReserva.setBounds(412, 325, 109, 23);
		}
		return btReserva;
	}

	private JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("Atras");
			btAtras.setMnemonic('A');
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

	private JTextField getTxtDisponibilidad() {
		if (txtDisponibilidad == null) {
			txtDisponibilidad = new JTextField();
			txtDisponibilidad.setHorizontalAlignment(SwingConstants.CENTER);
			txtDisponibilidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtDisponibilidad.setEditable(false);
			txtDisponibilidad.setBounds(332, 240, 159, 38);
			txtDisponibilidad.setColumns(10);
		}
		return txtDisponibilidad;
	}

	private JLabel getLblDisponibilidad() {
		if (lblDisponibilidad == null) {
			lblDisponibilidad = new JLabel("Disponibilidad");
			lblDisponibilidad.setHorizontalAlignment(SwingConstants.CENTER);
			lblDisponibilidad.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblDisponibilidad.setBounds(332, 198, 159, 20);
		}
		return lblDisponibilidad;
	}

	/**
	 * @SuppressWarnings("deprecation") private boolean
	 * comprobarReservaSimultanea() { int mesEscogido; if
	 * (cbMes.getSelectedIndex() == 0) { mesEscogido = mes; } else { mesEscogido
	 * = mes + 1; } if
	 * (Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]) -
	 * Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0])
	 * < 1) { Timestamp fecha = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]), 0, 0, 0); return
	 * bd.comprobarReservaSimultaneaUsuario(usuario.getIdentificador(), fecha);
	 * } else { Timestamp fecha1 = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]), 0, 0, 0);
	 * 
	 * Timestamp fecha2 = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0])
	 * , 0, 0, 0); return
	 * (bd.comprobarReservaSimultaneaUsuario(usuario.getIdentificador(), fecha1)
	 * && bd.comprobarReservaSimultaneaUsuario(usuario.getIdentificador(),
	 * fecha2)) ? true : false; } }
	 **/
	/**
	 * @SuppressWarnings("deprecation") private boolean
	 * comprobarDisponibilidadHora() { int mesEscogido; if
	 * (cbMes.getSelectedIndex() == 0) { mesEscogido = mes; } else { mesEscogido
	 * = mes + 1; } if
	 * (Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]) -
	 * Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0])
	 * < 1) { Timestamp fecha = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]), 0, 0, 0); return
	 * bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()),
	 * fecha); } else { Timestamp fecha1 = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(
	 * ":")[0]), 0, 0, 0);
	 * 
	 * Timestamp fecha2 = new Timestamp(aï¿½o, mesEscogido,
	 * Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
	 * Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0])
	 * , 0, 0, 0); return
	 * (bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()),
	 * fecha1) &&
	 * bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()),
	 * fecha2)) ? true : false; } }
	 **/

	private void generarHorasInicio() {
		int aux = Integer.parseInt(cbDia.getSelectedItem().toString());
		if (dia == aux) {
			int diferencia = 24 - hora;
			if (min != 0) {
				diferencia--;
				String[] horas = new String[diferencia-1];
				int j = hora + 2;
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
			}
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

	private void generarHorasFin(String horaInicio) {
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

	private JPanel getPnMetodoPago() {
		if (pnMetodoPago == null) {
			pnMetodoPago = new JPanel();
			pnMetodoPago.setBorder(
					new TitledBorder(null, "Forma de pago", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMetodoPago.setBounds(10, 280, 233, 68);
			pnMetodoPago.setLayout(null);
			pnMetodoPago.add(getRdbtnEfectivo());
			pnMetodoPago.add(getRdbtnCuota());
		}
		return pnMetodoPago;
	}

	private JRadioButton getRdbtnEfectivo() {
		if (rdbtnEfectivo == null) {
			rdbtnEfectivo = new JRadioButton("Efectivo");
			rdbtnEfectivo.setActionCommand("Hola");
			rdbtnEfectivo.setToolTipText("Seleccione si desea pagar en el momento de usar la instalac\u00EDon.");
			rdbtnEfectivo.setMnemonic('E');
			buttonGroup.add(rdbtnEfectivo);
			rdbtnEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnEfectivo.setBounds(6, 28, 89, 23);
		}
		return rdbtnEfectivo;
	}

	private JRadioButton getRdbtnCuota() {
		if (rdbtnCuota == null) {
			rdbtnCuota = new JRadioButton("Cuota Mensual");
			rdbtnCuota.setActionCommand("false");
			rdbtnCuota.setToolTipText("Selecciona Para que se a\u00F1ada el pago a su cuota mensual.");
			buttonGroup.add(rdbtnCuota);
			rdbtnCuota.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnCuota.setSelected(true);
			rdbtnCuota.setBounds(97, 28, 130, 23);
		}
		return rdbtnCuota;
	}
}