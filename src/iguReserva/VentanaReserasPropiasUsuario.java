package iguReserva;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logica.BaseDatos;
import logica.Usuario;

public class VentanaReserasPropiasUsuario extends JDialog {

	private static final long serialVersionUID = 6735821194521131468L;
	private JPanel pnFiltro;
	private JRadioButton rdbtnCanceladas;
	private JRadioButton rdbtnPendientes;
	private JRadioButton rdbtnRealizadas;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private Usuario usuario;
	private BaseDatos bd;
	private JPanel pnFechas;
	private JComboBox<String> cbDia;
	private JComboBox<String> cbMes;
	private JComboBox<String> cbAno;
	private JLabel lblDesde;
	private JComboBox<String> cbDiaH;
	private JComboBox<String> cbMesH;
	private JComboBox<String> cbAnoH;
	private JLabel lblHasta;
	private JButton btnMostrarReserva;
	private JButton btnAnularReserva;
	private JButton btnAtras;
	private DefaultTableModel modeloTablaReservas;
	private JScrollPane scrollPane;
	private JTable tbReservas;

	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	private int año = c.get(Calendar.YEAR);
	private int hora = c.get(Calendar.HOUR_OF_DAY);

	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

	/**
	 * Create the dialog.
	 */
	public VentanaReserasPropiasUsuario(Usuario usuario) {
		this.usuario = usuario;
		bd = new BaseDatos();
		setTitle("Reservas Propias");
		setBounds(100, 100, 683, 602);
		getContentPane().setLayout(null);
		getContentPane().add(getPnFechas());
		getContentPane().add(getBtnMostrarReserva());
		getContentPane().add(getBtnAnularReserva());
		getContentPane().add(getBtnAtras());
		getContentPane().add(getScrollPane_1());

		cargarDias();
		cargarAño();
		getContentPane().add(getPnFiltro());
	}

	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBorder(
					new TitledBorder(null, "Filtro Reservas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
			rdbtnCanceladas.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					borrarModelo();
					cbMes.setEnabled(true);
					cbDia.setEnabled(true);
					cbAno.setEnabled(true);
					cbMesH.setSelectedIndex(mes);
					cbMes.setSelectedIndex(mes);
					cbDia.setSelectedIndex(dia - 1);
					cbDiaH.setSelectedIndex(dia - 1);
					btnAnularReserva.setEnabled(false);
				}
			});
			buttonGroup.add(rdbtnCanceladas);
			rdbtnCanceladas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnCanceladas.setBounds(476, 27, 109, 23);
		}
		return rdbtnCanceladas;
	}

	private JRadioButton getRdbtnPendientes() {
		if (rdbtnPendientes == null) {
			rdbtnPendientes = new JRadioButton("Pendientes");
			rdbtnPendientes.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					borrarModelo();
					cbMes.setEnabled(false);
					cbDia.setEnabled(false);
					cbAno.setEnabled(false);
					cbMesH.setSelectedIndex(mes);
					cbMes.setSelectedIndex(mes);
					cbDia.setSelectedIndex(dia - 1);
					cbDiaH.setSelectedIndex(dia - 1);
					btnAnularReserva.setEnabled(true);
				}
			});
			buttonGroup.add(rdbtnPendientes);
			rdbtnPendientes.setSelected(true);
			rdbtnPendientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnPendientes.setBounds(250, 29, 109, 23);
		}
		return rdbtnPendientes;
	}

	private JRadioButton getRdbtnRealizadas() {
		if (rdbtnRealizadas == null) {
			rdbtnRealizadas = new JRadioButton("Realizadas");
			rdbtnRealizadas.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					borrarModelo();
					cbMes.setEnabled(true);
					cbDia.setEnabled(true);
					cbAno.setEnabled(true);
					cbMesH.setSelectedIndex(mes);
					cbMes.setSelectedIndex(mes);
					cbDia.setSelectedIndex(dia - 1);
					cbDiaH.setSelectedIndex(dia - 1);
					btnAnularReserva.setEnabled(false);
				}
			});
			buttonGroup.add(rdbtnRealizadas);
			rdbtnRealizadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnRealizadas.setBounds(44, 27, 109, 23);
		}
		return rdbtnRealizadas;
	}

	private JPanel getPnFechas() {
		if (pnFechas == null) {
			pnFechas = new JPanel();
			pnFechas.setBorder(new TitledBorder(null, "Fechas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFechas.setBounds(10, 104, 642, 91);
			pnFechas.setLayout(null);
			pnFechas.add(getCbDia());
			pnFechas.add(getCbMes());
			pnFechas.add(getCbAno());
			pnFechas.add(getLblDesde());
			pnFechas.add(getCbDiaH());
			pnFechas.add(getCbMesH());
			pnFechas.add(getCbAnoH());
			pnFechas.add(getLblHasta());
		}
		return pnFechas;
	}

	private JComboBox<String> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<String>();
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbDia.setBounds(10, 47, 59, 25);
		}
		return cbDia;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cargarDias();
				}
			});
			cbMes.setModel(new DefaultComboBoxModel<String>(meses));
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbMes.setBounds(79, 47, 129, 25);
		}
		return cbMes;
	}

	private JComboBox<String> getCbAno() {
		if (cbAno == null) {
			cbAno = new JComboBox<String>();
			cbAno.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbAno.setBounds(218, 47, 84, 25);
		}
		return cbAno;
	}

	private JLabel getLblDesde() {
		if (lblDesde == null) {
			lblDesde = new JLabel("Desde:");
			lblDesde.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblDesde.setBounds(10, 22, 71, 25);
		}
		return lblDesde;
	}

	private JComboBox<String> getCbDiaH() {
		if (cbDiaH == null) {
			cbDiaH = new JComboBox<String>();
			cbDiaH.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbDiaH.setBounds(329, 47, 59, 25);
		}
		return cbDiaH;
	}

	private JComboBox<String> getCbMesH() {
		if (cbMesH == null) {
			cbMesH = new JComboBox<String>();
			cbMesH.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cargarDias();
				}
			});
			cbMesH.setModel(new DefaultComboBoxModel<String>(meses));
			cbMesH.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbMesH.setBounds(398, 47, 129, 25);
		}
		return cbMesH;
	}

	private JComboBox<String> getCbAnoH() {
		if (cbAnoH == null) {
			cbAnoH = new JComboBox<String>();
			cbAnoH.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbAnoH.setBounds(535, 47, 84, 25);
		}
		return cbAnoH;
	}

	private JLabel getLblHasta() {
		if (lblHasta == null) {
			lblHasta = new JLabel("Hasta:");
			lblHasta.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblHasta.setBounds(329, 22, 59, 21);
		}
		return lblHasta;
	}

	/**
	 * Borra los elementos de la tabla
	 */
	private void borrarModelo() {
		while (modeloTablaReservas.getRowCount() > 0) {
			modeloTablaReservas.removeRow(0);
		}
	}

	private JButton getBtnMostrarReserva() {
		if (btnMostrarReserva == null) {
			btnMostrarReserva = new JButton("Mostrar Reserva");
			btnMostrarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarModelo();
					cargarElementosTabla();
				}
			});
			btnMostrarReserva.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnMostrarReserva.setBounds(130, 206, 147, 23);
		}
		return btnMostrarReserva;
	}

	@SuppressWarnings("deprecation")
	private void cargarElementosTabla() {
		List<String> result;
		if (rdbtnPendientes.isSelected()) {
			result = bd.cargarReservasPendientesUsuario(usuario, new Timestamp(año - 1900, mes, dia, 0, 0, 0, 0),
					new Timestamp(Integer.parseInt(cbAnoH.getItemAt(cbAnoH.getSelectedIndex())) - 1900,
							cbMesH.getSelectedIndex(), Integer.parseInt(cbDiaH.getItemAt(cbDiaH.getSelectedIndex())),
							23, 59, 0, 0));
		} else if (rdbtnRealizadas.isSelected()) {
			result = bd.cargarReservasRealizadasUsuario(usuario,
					new Timestamp(Integer.parseInt(cbAno.getItemAt(cbAno.getSelectedIndex())) - 1900,
							cbMes.getSelectedIndex(), Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 0,
							0, 0),
					new Timestamp(Integer.parseInt(cbAnoH.getItemAt(cbAnoH.getSelectedIndex())) - 1900,
							cbMesH.getSelectedIndex(), Integer.parseInt(cbDiaH.getItemAt(cbDiaH.getSelectedIndex())),
							23, 59, 0, 0));
		} else {
			result = bd.cargarReservasCanceladasUsuario(usuario,
					new Timestamp(Integer.parseInt(cbAno.getItemAt(cbAno.getSelectedIndex())) - 1900,
							cbMes.getSelectedIndex(), Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 0,
							0, 0),
					new Timestamp(Integer.parseInt(cbAnoH.getItemAt(cbAnoH.getSelectedIndex())) - 1900,
							cbMesH.getSelectedIndex(), Integer.parseInt(cbDiaH.getItemAt(cbDiaH.getSelectedIndex())),
							23, 59, 0, 0));
		}
		for (int i = 0; i < result.size(); i++) {
			String[] cachos;
			cachos = result.get(i).split(";");
			modeloTablaReservas.addRow(cachos);
		}
	}

	private JButton getBtnAnularReserva() {
		if (btnAnularReserva == null) {
			btnAnularReserva = new JButton("Anular Reserva");
			btnAnularReserva.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					if (tbReservas.getSelectedRow() != -1) {
						String nombre = (String) tbReservas.getValueAt(tbReservas.getSelectedRow(), 0);
						String horaInicio = (String) tbReservas.getValueAt(tbReservas.getSelectedRow(), 1);
						String horaFin = (String) tbReservas.getValueAt(tbReservas.getSelectedRow(), 2);
						// Comprobacion de que la cancelacion se haga como
						// minimo una hora antes de el unicio de la reserva.
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
							Date parsedDate = dateFormat.parse(horaInicio);
							Timestamp timestampReserva = new java.sql.Timestamp(parsedDate.getTime());
							Timestamp timestampActual = new Timestamp(año - 1900, mes, dia, hora, 0, 0, 0);
//							if(timestampActual.before(timestampReserva)){
//								
//							}
							if (timestampActual.getYear() <= timestampReserva.getYear()) {
								if (timestampActual.getMonth() <= timestampReserva.getMonth()) {
									if (timestampActual.getDate() <= timestampReserva.getDate()) {
										if (timestampActual.getHours() >= timestampReserva.getHours() - 1) {
											JOptionPane.showMessageDialog(null,
													"No se puede cancelar una hora con menos de una hora de antelación,disculpe las molestias.",
													"ERROR", JOptionPane.ERROR_MESSAGE);
										} else {
											bd.cancelarReservaUsuario(usuario, nombre, horaInicio, horaFin);
											JOptionPane.showMessageDialog(null, "Su reserva ha sido borrada con exito.",
													"Informacion", JOptionPane.INFORMATION_MESSAGE);
											borrarModelo();
											cargarElementosTabla();
										}
									} else {
										bd.cancelarReservaUsuario(usuario, nombre, horaInicio, horaFin);
										JOptionPane.showMessageDialog(null, "Su reserva ha sido borrada con exito.",
												"Informacion", JOptionPane.INFORMATION_MESSAGE);
										borrarModelo();
										cargarElementosTabla();
									}
								} else {
									bd.cancelarReservaUsuario(usuario, nombre, horaInicio, horaFin);
									JOptionPane.showMessageDialog(null, "Su reserva ha sido borrada con exito.",
											"Informacion", JOptionPane.INFORMATION_MESSAGE);
									borrarModelo();
									cargarElementosTabla();
								}
							} else {
								bd.cancelarReservaUsuario(usuario, nombre, horaInicio, horaFin);
								JOptionPane.showMessageDialog(null, "Su reserva ha sido borrada con exito.",
										"Informacion", JOptionPane.INFORMATION_MESSAGE);
								borrarModelo();
								cargarElementosTabla();
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				}
			});
			btnAnularReserva.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnAnularReserva.setBounds(372, 206, 147, 23);
		}
		return btnAnularReserva;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnAtras.setBounds(563, 530, 89, 23);
		}
		return btnAtras;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 240, 642, 279);
			scrollPane.setViewportView(getTbReservas());
		}
		return scrollPane;
	}

	private JTable getTbReservas() {
		if (tbReservas == null) {
			modeloTablaReservas = new DefaultTableModel(new Object[] { "Sala", "Hora Inicio", "Hora Fin" }, 0);
			tbReservas = new JTable(modeloTablaReservas);
			tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbReservas.getTableHeader().setReorderingAllowed(false);
		}
		return tbReservas;
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

	/**
	 * Metodo que carga el numero de dias del mes que se ha seleccionado en el
	 * combobox.
	 */
	private void cargarDias() {
		cbDia.setModel(
				new DefaultComboBoxModel<String>(calcularDiasMes((String) cbMes.getItemAt(cbMes.getSelectedIndex()))));
		cbDiaH.setModel(new DefaultComboBoxModel<String>(
				calcularDiasMes((String) cbMesH.getItemAt(cbMesH.getSelectedIndex()))));
	}

	/**
	 * Metodo que carga el años en el combobox dedicado a tal efecto.Se cargara
	 * el año en curso y el siguiente.
	 */
	private void cargarAño() {
		String[] años = { año + "", año + 1 + "" };
		cbAno.setModel(new DefaultComboBoxModel<String>(años));
		cbAnoH.setModel(new DefaultComboBoxModel<String>(años));
	}
}
