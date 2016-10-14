package iguReserva;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logica.BaseDatos;
import logica.Reserva;
import logica.Sala;
import logica.Usuario;

import javax.swing.SwingConstants;

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
	private JTextField txDisponibilidad;
	private JLabel lblDisponibilidad;
	private JButton btnComprobar;

	private Usuario usuario;
	private BaseDatos bd;
	private List<Sala> salasGimnasio;

	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	private int año = c.get(Calendar.YEAR) - 1900;
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int min = c.get(Calendar.MINUTE);

	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

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
		getContentPane().add(getTxDisponibilidad());
		getContentPane().add(getLblDisponibilidad());
		getContentPane().add(getBtnComprobar());

		cbDia.setSelectedIndex(0);
		cbMes.setSelectedIndex(0);
		cbSalas.setSelectedIndex(0);
		txAno.setText(c.get(Calendar.YEAR) + "");

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
					desactivarReserva();
					ponerPrecio();
				}
			});
			int tamaño = salasGimnasio.size();
			String[] salas = new String[tamaño];
			for (int i = 0; i < salasGimnasio.size(); i++)
				salas[i] = salasGimnasio.get(i).getCodigo();
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbSalas.setBounds(10, 123, 97, 28);
			cbSalas.setModel(new DefaultComboBoxModel<String>(salas));
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
			lbPrecio.setBounds(122, 99, 46, 14);
		}
		return lbPrecio;
	}

	private JTextField getTxPrecio() {
		if (txPrecio == null) {
			txPrecio = new JTextField();
			txPrecio.setHorizontalAlignment(SwingConstants.CENTER);
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
					desactivarReserva();
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
					desactivarReserva();
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
					desactivarReserva();
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
			cbFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					desactivarReserva();
				}
			});
			cbFin.setBounds(109, 42, 89, 26);
		}
		return cbFin;
	}

	private JButton getBtReserva() {
		if (btReserva == null) {
			btReserva = new JButton("Reservar");
			btReserva.setEnabled(false);
			btReserva.setMnemonic('R');
			btReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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

	private JTextField getTxDisponibilidad() {
		if (txDisponibilidad == null) {
			txDisponibilidad = new JTextField();
			txDisponibilidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txDisponibilidad.setEditable(false);
			txDisponibilidad.setBounds(341, 276, 159, 38);
			txDisponibilidad.setColumns(10);
		}
		return txDisponibilidad;
	}

	private JLabel getLblDisponibilidad() {
		if (lblDisponibilidad == null) {
			lblDisponibilidad = new JLabel("Disponibilidad");
			lblDisponibilidad.setHorizontalAlignment(SwingConstants.CENTER);
			lblDisponibilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblDisponibilidad.setBounds(341, 245, 159, 20);
		}
		return lblDisponibilidad;
	}

	private JButton getBtnComprobar() {
		if (btnComprobar == null) {
			btnComprobar = new JButton("Comprobar");
			btnComprobar.setMnemonic('C');
			btnComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comprobarDisponibilidadHora()) {
						System.out.println("Disponible");
					} else {
						System.out.println("Reservada");
					}
				}
			});
			btnComprobar.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnComprobar.setBounds(358, 196, 121, 38);
		}
		return btnComprobar;
	}

	@SuppressWarnings("deprecation")
	private boolean comprobarDisponibilidadHora() {
		int mesEscogido;
		if (cbMes.getSelectedIndex() == 0) {
			mesEscogido = mes;
		} else {
			mesEscogido = mes + 1;
		}
		if (Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0])
				- Integer.parseInt(cbInicio.getItemAt(cbFin.getSelectedIndex()).split(":")[0]) < 1) {
			Timestamp fecha = new Timestamp(año, mesEscogido,
					Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
					Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]), 0, 0, 0);
			return bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()), fecha);
		} else {
			Timestamp fecha1 = new Timestamp(año, mesEscogido,
					Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
					Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]), 0, 0, 0);

			Timestamp fecha2 = new Timestamp(año, mesEscogido,
					Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
					Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0]), 0, 0, 0);
			return (bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()), fecha1)
					&& bd.comprobarReservaSala(cbSalas.getItemAt(cbSalas.getSelectedIndex()), fecha2)) ? true : false;
		}
	}

	private void generarHorasInicio() {
		int aux = Integer.parseInt(cbDia.getSelectedItem().toString());
		if (dia == aux) {
			int diferencia = 24 - hora;
			if (min != 0) {
				diferencia--;
				String[] horas = new String[diferencia - 1];
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

	private void desactivarReserva() {
		txDisponibilidad.setText("");
		txDisponibilidad.setBackground(null);
		btReserva.setEnabled(false);
	}
}
