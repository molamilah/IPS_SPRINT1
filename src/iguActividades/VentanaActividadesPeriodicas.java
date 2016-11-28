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

public class VentanaActividadesPeriodicas extends JDialog {

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
	private Calendar fechaInicial;
	private Calendar fechaFinal;
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int min = c.get(Calendar.MINUTE);
	private JLabel lbInicio;
	private JComboBox<String> cbFin;
	private JComboBox<String> cbInicio;
	private JLabel lbFin;
	private JComboBox<String> cbAnnoInicial;
	private JComboBox<String> cbMesInicial;
	private JComboBox<String> cbDiaInicial;
	private JComboBox<String> cbAnnoFinal;
	private JComboBox<String> cbMesFinal;
	private JComboBox<String> cbDiaFinal;
	private JComboBox<String> cbDiaSemana;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JLabel lblAnno;
	private JLabel lblMes;
	private JLabel lblDia;
	private BaseDatos bd;
	private JLabel lblSemana;
	private JButton btnValidarFecha;
	private JButton btnCambiar;
	private JCheckBox chkDiaCompleto;
	private JComboBox<String> cbSalas;
	private List<Sala> salasGimnasio;
	private JPanel panel;
	private Object[] actividad;
	private boolean creada;
	private int idActividad;

	public VentanaActividadesPeriodicas(Object[] actividad) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				BBDDReservasActividades.desconectar();
			}
		});
		setResizable(false);
		this.actividad = actividad;
		this.creada = false;
		setTitle("Admin: Reservas Periodicas");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaActividadesPeriodicas.class.getResource("/img/img-recepcion-reducida.jpg")));
		setBounds(100, 100, 652, 551);
		setBounds(100, 100, 652, 467);
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
		getContentPane().add(getBtnValidarFecha());
		getContentPane().add(getBtnCambiar());
		getContentPane().add(getChkDiaCompleto());
		getContentPane().add(getPanel());
		rellenarCbAnno();
		rellenarDiaSemana();

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
		if (fechaInicial.get(Calendar.YEAR) == aux.get(Calendar.YEAR)
				&& fechaInicial.get(Calendar.MONTH) == aux.get(Calendar.MONTH)
				&& fechaInicial.get(Calendar.DAY_OF_MONTH) == aux.get(Calendar.DAY_OF_MONTH)) {
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
			pnFecha.setBounds(47, 73, 564, 139);
			pnFecha.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFecha.setLayout(null);
			pnFecha.add(getCbAnnoInicial());
			pnFecha.add(getCbMesInicial());
			pnFecha.add(getCbDiaInicial());
			pnFecha.add(getCbAnnoFinal());
			pnFecha.add(getCbMesFinal());
			pnFecha.add(getCbDiaFinal());
			pnFecha.add(getCbDiaSemana());
			pnFecha.add(getLblDesde());
			pnFecha.add(getLblHasta());
			pnFecha.add(getLblAnno());
			pnFecha.add(getLblMes());
			pnFecha.add(getLblDia());
			pnFecha.add(getLblSemana());
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
		cbAnnoInicial.setModel(new DefaultComboBoxModel<String>(annos));
		cbAnnoFinal.setModel(new DefaultComboBoxModel<String>(annos));

		// cbAnnoInicial.setSelectedIndex(-1);
		// cbAnnoFinal.setSelectedIndex(-1);
	}

	private void rellenarCbMesInicial() {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		cbMesInicial.setModel(new DefaultComboBoxModel<String>(meses));
		// cbMesInicial.setSelectedIndex(-1);
		// cbDiaInicial.setSelectedItem(-1);
	}

	private void rellenarCbMesFinal() {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		cbMesFinal.setModel(new DefaultComboBoxModel<String>(meses));
		// cbMesFinal.setSelectedIndex(-1);
		// cbDiaFinal.setSelectedItem(-1);
	}

	private void rellenarCbDiaInicial() {
		Calendar aux = Calendar.getInstance();
		aux.set(Calendar.YEAR, Integer.parseInt(cbAnnoInicial.getItemAt(cbAnnoInicial.getSelectedIndex())));
		aux.set(Calendar.MONTH, cbMesInicial.getSelectedIndex());
		int maximo = aux.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] dias = new String[maximo];
		for (int i = 1; i <= maximo; i++) {
			dias[i - 1] = String.valueOf(i);
		}
		cbDiaInicial.setModel(new DefaultComboBoxModel<String>(dias));
		cbDiaInicial.setEnabled(true);
		// cbDiaInicial.setSelectedIndex(-1);
	}

	private void rellenarCbDiaFinal() {
		Calendar aux = Calendar.getInstance();
		aux.set(Calendar.YEAR, Integer.parseInt(cbAnnoInicial.getItemAt(cbAnnoInicial.getSelectedIndex())));
		aux.set(Calendar.MONTH, cbMesFinal.getSelectedIndex());
		int maximo = aux.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] dias = new String[maximo];
		for (int i = 1; i <= maximo; i++) {
			dias[i - 1] = String.valueOf(i);
		}
		cbDiaFinal.setModel(new DefaultComboBoxModel<String>(dias));
		cbDiaFinal.setEnabled(true);
		// cbDiaInicial.setSelectedIndex(-1);
	}

	private void rellenarDiaSemana() {
		String[] semana = { "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" };
		cbDiaSemana.setModel(new DefaultComboBoxModel<String>(semana));
	}

	private JPanel getPnHora() {
		if (pnHora == null) {
			pnHora = new JPanel();
			pnHora.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Horario",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnHora.setBounds(30, 326, 212, 82);
			pnHora.setBorder(new TitledBorder(null, "Horario( Max 2 horas)", TitledBorder.LEADING, TitledBorder.TOP,
					null, null));
			pnHora.setBounds(38, 296, 212, 82);
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
						Reservador.reservarPeriodico(0, fechaInicial, fechaFinal, 0, 24,
								cbDiaSemana.getSelectedIndex() + 1, cbSalas.getItemAt(cbSalas.getSelectedIndex()),
								idActividad);
					} else {
						Reservador.reservarPeriodico(0, fechaInicial, fechaFinal,
								Integer.parseInt(cbInicio.getItemAt(cbInicio.getSelectedIndex()).split(":")[0]),
								Integer.parseInt(cbFin.getItemAt(cbFin.getSelectedIndex()).split(":")[0]) + 1,
								cbDiaSemana.getSelectedIndex() + 1, cbSalas.getItemAt(cbSalas.getSelectedIndex()),
								idActividad);
					}
					chkDiaCompleto.setSelected(false);
					chkDiaCompleto.setEnabled(false);
					cbInicio.setEnabled(false);
					cbFin.setEnabled(false);
					cbDiaFinal.setEnabled(false);
					cbMesFinal.setEnabled(false);
					cbAnnoFinal.setEnabled(false);
					cbDiaInicial.setEnabled(false);
					cbMesInicial.setEnabled(false);
					btnCambiar.setEnabled(false);
					btnValidarFecha.setEnabled(true);
					btnReservar.setEnabled(false);
					cbAnnoInicial.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(), "Operacion realizada correctamente");
				}
			});
			btnReservar.setBounds(522, 380, 89, 34);
			btnReservar.setBounds(497, 380, 89, 34);
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
			btnAtras.setBounds(350, 454, 95, 34);
			btnAtras.setBounds(351, 380, 95, 34);
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

	private JComboBox<String> getCbAnnoInicial() {
		if (cbAnnoInicial == null) {
			cbAnnoInicial = new JComboBox<String>();
			cbAnnoInicial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rellenarCbMesInicial();
					cbDiaInicial.setEnabled(false);
					cbMesInicial.setEnabled(true);
				}
			});
			cbAnnoInicial.setBounds(76, 43, 86, 20);
		}
		return cbAnnoInicial;
	}

	private JComboBox<String> getCbMesInicial() {
		if (cbMesInicial == null) {
			cbMesInicial = new JComboBox<String>();
			cbMesInicial.setEnabled(false);
			cbMesInicial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rellenarCbDiaInicial();
				}
			});
			cbMesInicial.setBounds(203, 43, 99, 20);
		}
		return cbMesInicial;
	}

	private JComboBox<String> getCbDiaInicial() {
		if (cbDiaInicial == null) {
			cbDiaInicial = new JComboBox<String>();
			cbDiaInicial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Calendar aux = Calendar.getInstance();
					aux.set(Calendar.YEAR, Integer.parseInt(cbAnnoInicial.getItemAt(cbAnnoInicial.getSelectedIndex())));
					aux.set(Calendar.MONTH, cbMesInicial.getSelectedIndex());
					aux.set(Calendar.DAY_OF_MONTH,
							Integer.parseInt(cbDiaInicial.getItemAt(cbDiaInicial.getSelectedIndex())));
					if (c.get(Calendar.HOUR_OF_DAY) >= 23) {
						JOptionPane.showMessageDialog(getContentPane(),
								"No se puede reservar para el mismo dia mas "
										+ "tarde de las 23h debido a que se deben hacer con al menos 1h de antelacion."
										+ "\nPor favor cambie el dia de su reserva al siguiente");
						cbDiaInicial.setSelectedIndex(-1);

					} else if (c.after(aux)) {
						JOptionPane.showMessageDialog(getContentPane(),
								"La fecha inicial debe ser superior a la actual");
						cbMesInicial.setSelectedIndex(-1);
						cbMesInicial.setEnabled(false);
						cbDiaInicial.setEnabled(false);
						cbDiaInicial.setSelectedIndex(-1);
					} else {
						fechaInicial = aux;
						cbAnnoFinal.setEnabled(true);
					}
				}
			});
			cbDiaInicial.setBounds(342, 43, 62, 20);
			cbDiaInicial.setEnabled(false);
		}
		return cbDiaInicial;
	}

	private JComboBox<String> getCbAnnoFinal() {
		if (cbAnnoFinal == null) {
			cbAnnoFinal = new JComboBox<String>();
			cbAnnoFinal.setEnabled(false);
			cbAnnoFinal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rellenarCbMesFinal();
					cbDiaFinal.setEnabled(false);
					cbMesFinal.setEnabled(true);
				}
			});
			cbAnnoFinal.setBounds(76, 94, 86, 20);
		}
		return cbAnnoFinal;
	}

	private JComboBox<String> getCbMesFinal() {
		if (cbMesFinal == null) {
			cbMesFinal = new JComboBox<String>();
			cbMesFinal.setEnabled(false);
			cbMesFinal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rellenarCbDiaFinal();
				}
			});
			cbMesFinal.setBounds(203, 94, 99, 20);
		}
		return cbMesFinal;
	}

	private JComboBox<String> getCbDiaFinal() {
		if (cbDiaFinal == null) {
			cbDiaFinal = new JComboBox<String>();
			cbDiaFinal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnValidarFecha.setEnabled(true);
				}
			});
			cbDiaFinal.setBounds(342, 94, 62, 20);
			cbDiaFinal.setEnabled(false);
		}
		return cbDiaFinal;
	}

	private JComboBox<String> getCbDiaSemana() {
		if (cbDiaSemana == null) {
			cbDiaSemana = new JComboBox<String>();
			cbDiaSemana.setBounds(437, 58, 99, 28);
		}
		return cbDiaSemana;
	}

	private JLabel getLblDesde() {
		if (lblDesde == null) {
			lblDesde = new JLabel("Desde:");
			lblDesde.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDesde.setBounds(10, 46, 46, 14);
		}
		return lblDesde;
	}

	private JLabel getLblHasta() {
		if (lblHasta == null) {
			lblHasta = new JLabel("Hasta:");
			lblHasta.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblHasta.setBounds(10, 97, 46, 14);
		}
		return lblHasta;
	}

	private JLabel getLblAnno() {
		if (lblAnno == null) {
			lblAnno = new JLabel("Año");
			lblAnno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblAnno.setBounds(103, 18, 46, 14);
		}
		return lblAnno;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes");
			lblMes.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblMes.setBounds(233, 18, 46, 14);
		}
		return lblMes;
	}

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Dia");
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblDia.setBounds(358, 18, 46, 14);
		}
		return lblDia;
	}

	private JLabel getLblSemana() {
		if (lblSemana == null) {
			lblSemana = new JLabel("Dia de la Semana");
			lblSemana.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblSemana.setBounds(427, 10, 127, 31);
		}
		return lblSemana;
	}

	private JButton getBtnValidarFecha() {
		if (btnValidarFecha == null) {
			btnValidarFecha = new JButton("Validar Fecha");
			btnValidarFecha.setBounds(384, 223, 142, 34);
			btnValidarFecha = new JButton("Validar");
			btnValidarFecha.setBounds(369, 231, 89, 34);
			btnValidarFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Calendar inicio = Calendar.getInstance();
					Calendar fin = Calendar.getInstance();
					inicio.set(Calendar.YEAR,
							Integer.parseInt(cbAnnoInicial.getItemAt((cbAnnoInicial.getSelectedIndex()))));
					inicio.set(Calendar.MONTH, cbMesInicial.getSelectedIndex());
					inicio.set(Calendar.DAY_OF_MONTH, cbDiaInicial.getSelectedIndex() + 1);
					inicio.set(Calendar.HOUR_OF_DAY, 0);

					fin.set(Calendar.YEAR, Integer.parseInt(cbAnnoFinal.getItemAt((cbAnnoFinal.getSelectedIndex()))));
					fin.set(Calendar.MONTH, cbMesFinal.getSelectedIndex());
					fin.set(Calendar.DAY_OF_MONTH, cbDiaFinal.getSelectedIndex() + 1);
					fin.set(Calendar.HOUR_OF_DAY, 0);
					if (!fin.after(inicio))
						JOptionPane.showMessageDialog(getContentPane(),
								"La fecha final ha de ser mayor que la inicial");
					else {
						generarHorasInicio();
						fechaInicial = inicio;
						fechaFinal = fin;
						btnCambiar.setEnabled(true);
						btnValidarFecha.setEnabled(false);
						cbDiaFinal.setEnabled(false);
						cbMesFinal.setEnabled(false);
						cbAnnoFinal.setEnabled(false);
						cbDiaInicial.setEnabled(false);
						cbMesInicial.setEnabled(false);
						cbAnnoInicial.setEnabled(false);
						chkDiaCompleto.setEnabled(true);
					}
				}
			});
			btnValidarFecha.setEnabled(false);
		}
		return btnValidarFecha;
	}

	private JButton getBtnCambiar() {
		if (btnCambiar == null) {
			btnCambiar = new JButton("Cambiar Fecha");
			btnCambiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cbAnnoInicial.setEnabled(true);
					cbDiaInicial.setEnabled(false);
					cbDiaFinal.setEnabled(false);
					cbDiaSemana.setEnabled(true);
					cbInicio.setEnabled(false);
					cbFin.setEnabled(false);
					btnReservar.setEnabled(false);
					chkDiaCompleto.setEnabled(false);
					btnCambiar.setEnabled(false);
				}
			});
			btnCambiar.setEnabled(false);
			btnCambiar.setBounds(163, 223, 142, 34);
		}
		return btnCambiar;
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
			chkDiaCompleto.setBounds(91, 386, 97, 23);
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
			panel.setBounds(371, 287, 190, 82);
			panel.setLayout(null);
			panel.add(getCbSalas());
		}
		return panel;
	}

}