package iguActividades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import logica.BBDDReservasActividades;
import logica.BaseDatos;
import logica.Sala;
import reservas.Reservador;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaActividadUnica extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnFecha;
	private JPanel pnHora;
	private JButton btnReservar;
	private JButton btnAtras;
	private JLabel lblTitulo;
	private Calendar c = Calendar.getInstance();
	private Calendar fechaReserva;
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int min = c.get(Calendar.MINUTE);
	private JLabel lbInicio;
	private JComboBox<String> cbFin;
	private JComboBox<String> cbInicio;
	private JLabel lbFin;
	private JComboBox<String> cbAnno;
	private JComboBox<String> cbMes;
	private JComboBox<String> cbDia;
	private JLabel lblAnno;
	private JLabel lblMes;
	private JLabel lblDia;
	private BaseDatos bd;
	private JCheckBox chkDiaCompleto;
	private JComboBox<String> cbSalas;
	private List<Sala> salasGimnasio;
	private JPanel panel;
	private Object[] actividad;
	private int idActividad;
	private boolean creada;

	public VentanaActividadUnica(Object[] actividad) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				BBDDReservasActividades.desconectar();
			}
		});
		setResizable(false);
		this.actividad = actividad;
		this.creada = false;
		setTitle("Admin: Reserva Unica");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaActividadUnica.class.getResource("/img/img-recepcion-reducida.jpg")));
		setBounds(100, 100, 599, 411);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		bd = new BaseDatos();
		salasGimnasio = bd.cargarSalas();
		BBDDReservasActividades.conectar();
		getContentPane().add(getPnFecha());
		getContentPane().add(getPnHora());
		getContentPane().add(getBtnReservar());
		getContentPane().add(getBtnAtras());
		getContentPane().add(getLblTitulo());
		getContentPane().add(getPanel());
		getContentPane().add(getChkDiaCompleto());
		rellenarCbAnno();
		rellenarCbMesInicial();

	}

	private JComboBox<String> getCbInicio() {
		if (cbInicio == null) {
			cbInicio = new JComboBox<String>();
			cbInicio.setEnabled(false);
			cbInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarHorasFin(cbInicio.getItemAt(cbInicio.getSelectedIndex()));
				}
			});
			cbInicio.setBounds(10, 45, 89, 26);
		}
		return cbInicio;
	}

	private JComboBox<String> getCbFin() {
		if (cbFin == null) {
			cbFin = new JComboBox<String>();
			cbFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnReservar.setEnabled(true);
				}
			});
			cbFin.setEnabled(false);
			cbFin.setBounds(109, 45, 89, 26);
		}
		return cbFin;
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

	private void generarHorasInicio() {
		Calendar aux = Calendar.getInstance();
		if (fechaReserva.get(Calendar.YEAR) == aux.get(Calendar.YEAR)
				&& fechaReserva.get(Calendar.MONTH) == aux.get(Calendar.MONTH)
				&& fechaReserva.get(Calendar.DAY_OF_MONTH) == aux.get(Calendar.DAY_OF_MONTH)) {
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
		cbInicio.setEnabled(true);
	}

	private void generarHorasFin(String horaInicio) {
		int hora = Integer.parseInt(horaInicio.split(":")[0]);
		int tamano = 24 - hora;
		String[] horas = new String[tamano];
		for (int i = 0; i < tamano; i++) {
			if (hora < 10) {
				horas[i] = "0" + hora + ":59";
				hora++;
			} else {
				horas[i] = hora + ":59";
				hora++;
			}
		}
		cbFin.setModel(new DefaultComboBoxModel<String>(horas));
		cbFin.setEnabled(true);
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

	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setBounds(91, 83, 393, 93);
			pnFecha.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFecha.setLayout(null);
			pnFecha.add(getCbAnno());
			pnFecha.add(getCbMes());
			pnFecha.add(getCbDia());
			pnFecha.add(getLblAnno());
			pnFecha.add(getLblMes());
			pnFecha.add(getLblDia());
		}
		return pnFecha;
	}

	private void rellenarCbAnno() {
		String[] annos = new String[10];
		int j = 0;
		for (int i = c.get(Calendar.YEAR); i < c.get(Calendar.YEAR) + 10; i++) {
			annos[j] = String.valueOf(i);
			j++;
		}
		cbAnno.setModel(new DefaultComboBoxModel<String>(annos));

		// cbAnnoInicial.setSelectedIndex(-1);
		// cbAnnoFinal.setSelectedIndex(-1);
	}

	private void rellenarCbMesInicial() {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		cbMes.setModel(new DefaultComboBoxModel<String>(meses));
		// cbMesInicial.setSelectedIndex(-1);
		// cbDiaInicial.setSelectedItem(-1);
	}

	private void rellenarCbDiaInicial() {
		Calendar aux = Calendar.getInstance();
		aux.set(Calendar.YEAR, Integer.parseInt(cbAnno.getItemAt(cbAnno.getSelectedIndex())));
		aux.set(Calendar.MONTH, cbMes.getSelectedIndex());
		int maximo = aux.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] dias = new String[maximo];
		for (int i = 1; i <= maximo; i++) {
			dias[i - 1] = String.valueOf(i);
		}
		cbDia.setModel(new DefaultComboBoxModel<String>(dias));
		cbDia.setEnabled(true);
		// cbDiaInicial.setSelectedIndex(-1);
	}

	private JPanel getPnHora() {
		if (pnHora == null) {
			pnHora = new JPanel();
			pnHora.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Horario",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnHora.setBounds(51, 187, 212, 82);
			pnHora.setBorder(new TitledBorder(null, "Horario( Max 2 horas)", TitledBorder.LEADING, TitledBorder.TOP,
					null, null));
			pnHora.setLayout(null);
			pnHora.add(getLbInicio());
			pnHora.add(getCbInicio());
			pnHora.add(getLbFin());
			pnHora.add(getCbFin());
		}
		return pnHora;
	}

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!creada) {
						idActividad = BBDDReservasActividades.crearActividad((String) actividad[0],
								(String) actividad[1], (Integer) actividad[2], (Integer) actividad[3]);
						creada = true;
					}
					if (chkDiaCompleto.isSelected()) {
						Reservador.reservarUnica(0, cbSalas.getItemAt(cbSalas.getSelectedIndex()),
								Integer.parseInt(cbAnno.getItemAt(cbAnno.getSelectedIndex())), cbMes.getSelectedIndex(),
								Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 24, idActividad);
					} else {
						Reservador.reservarUnica(0, cbSalas.getItemAt(cbSalas.getSelectedIndex()),
								Integer.parseInt(cbAnno.getItemAt(cbAnno.getSelectedIndex())), cbMes.getSelectedIndex(),
								Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())),
								Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]),
								Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0]), idActividad);
					}
					chkDiaCompleto.setSelected(false);
					chkDiaCompleto.setEnabled(false);
					cbInicio.setEnabled(false);
					cbFin.setEnabled(false);
					cbDia.setEnabled(false);
					btnReservar.setEnabled(false);
					JOptionPane.showMessageDialog(getContentPane(), "Operacion realizada correctamente");
				}
			});
			btnReservar.setBounds(356, 321, 106, 34);
			btnReservar.setEnabled(false);
		}
		return btnReservar;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BBDDReservasActividades.desconectar();
					dispose();
				}
			});
			btnAtras.setBounds(162, 321, 119, 34);
		}
		return btnAtras;
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Reserva Periodica de Instalaciones para actividades\r\n");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitulo.setBounds(91, 28, 470, 34);
		}
		return lblTitulo;
	}

	private JComboBox<String> getCbAnno() {
		if (cbAnno == null) {
			cbAnno = new JComboBox<String>();
			cbAnno.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cbDia.setEnabled(false);
					cbMes.setEnabled(true);
				}
			});
			cbAnno.setBounds(38, 43, 86, 20);
		}
		return cbAnno;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rellenarCbDiaInicial();
					cbDia.setEnabled(true);
				}
			});
			cbMes.setBounds(162, 43, 99, 20);
		}
		return cbMes;
	}

	private JComboBox<String> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<String>();
			cbDia.setEnabled(false);
			cbDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Calendar aux = Calendar.getInstance();
					aux.set(Calendar.YEAR, Integer.parseInt(cbAnno.getItemAt(cbAnno.getSelectedIndex())));
					aux.set(Calendar.MONTH, cbMes.getSelectedIndex());
					aux.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())));
					if (c.get(Calendar.HOUR_OF_DAY) >= 23) {
						JOptionPane.showMessageDialog(getContentPane(),
								"No se puede reservar para el mismo dia mas "
										+ "tarde de las 23h debido a que se deben hacer con al menos 1h de antelacion."
										+ "\nPor favor cambie el dia de su reserva al siguiente");
						cbDia.setSelectedIndex(-1);

					} else if (c.after(aux)) {
						JOptionPane.showMessageDialog(getContentPane(),
								"La fecha inicial debe ser superior a la actual");
						cbMes.setSelectedIndex(-1);
						cbMes.setEnabled(false);
						cbDia.setEnabled(false);
						cbDia.setSelectedIndex(-1);
					} else {
						fechaReserva = aux;
						generarHorasInicio();
						cbInicio.setEnabled(true);
						chkDiaCompleto.setEnabled(true);
					}
				}
			});
			cbDia.setBounds(301, 43, 62, 20);
		}
		return cbDia;
	}

	private JLabel getLblAnno() {
		if (lblAnno == null) {
			lblAnno = new JLabel("Año");
			lblAnno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblAnno.setBounds(38, 18, 46, 14);
		}
		return lblAnno;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes");
			lblMes.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblMes.setBounds(162, 18, 46, 14);
		}
		return lblMes;
	}

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Dia");
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblDia.setBounds(301, 18, 46, 14);
		}
		return lblDia;
	}

	private JCheckBox getChkDiaCompleto() {
		if (chkDiaCompleto == null) {
			chkDiaCompleto = new JCheckBox("Todo el dia");
			chkDiaCompleto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (cbInicio.isEnabled()) {
						cbInicio.setEnabled(false);
						cbFin.setEnabled(false);
						btnReservar.setEnabled(true);
					} else {
						cbInicio.setEnabled(true);
						btnReservar.setEnabled(false);
					}
				}
			});
			chkDiaCompleto.setEnabled(false);
			chkDiaCompleto.setBounds(110, 276, 97, 23);
		}
		return chkDiaCompleto;
	}

	private JComboBox<String> getCbSalas() {
		if (cbSalas == null) {
			cbSalas = new JComboBox<String>();
			cbSalas.setBounds(33, 26, 127, 33);
			int tamano = salasGimnasio.size();
			String[] salas = new String[tamano];
			for (int i = 0; i < salasGimnasio.size(); i++)
				salas[i] = salasGimnasio.get(i).getDescripcion();
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbSalas.setModel(new DefaultComboBoxModel<String>(salas));
			cbSalas.setSelectedIndex(0);
		}
		return cbSalas;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Sala", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(358, 187, 190, 82);
			panel.setLayout(null);
			panel.add(getCbSalas());
		}
		return panel;
	}

}