package iguReserva;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logica.BaseDatos;
import logica.Usuario;

import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.awt.event.ActionEvent;

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
	private int a�o = c.get(Calendar.YEAR);

	/**
	 * Create the dialog.
	 */
	public VentanaReserasPropiasUsuario(Usuario usuario) {
		this.usuario = usuario;
		bd = new BaseDatos();
		setTitle("Reservas Propias");
		setBounds(100, 100, 683, 602);
		getContentPane().setLayout(null);
		getContentPane().add(getPnFiltro());
		getContentPane().add(getPnFechas());
		getContentPane().add(getBtnMostrarReserva());
		getContentPane().add(getBtnAnularReserva());
		getContentPane().add(getBtnAtras());
		getContentPane().add(getScrollPane_1());
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
			buttonGroup.add(rdbtnCanceladas);
			rdbtnCanceladas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			rdbtnCanceladas.setBounds(476, 27, 109, 23);
		}
		return rdbtnCanceladas;
	}

	private JRadioButton getRdbtnPendientes() {
		if (rdbtnPendientes == null) {
			rdbtnPendientes = new JRadioButton("Pendientes");
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
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbDia.setBounds(10, 47, 59, 25);
		}
		return cbDia;
	}

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<String>();
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbMes.setBounds(79, 47, 129, 25);
		}
		return cbMes;
	}

	private JComboBox<String> getCbAno() {
		if (cbAno == null) {
			cbAno = new JComboBox<String>();
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
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					borrarModelo();
					// llamar a un metodo de la base de datos que me coja las
					// reservas del usuario entre las fechas seleccionadas
					// comprobar que no este cancelada.
					if (rdbtnPendientes.isSelected()) {
						bd.cargarReservasPendientesUsuario(usuario, new Timestamp(a�o-1900, mes, dia, 0, 0, 0, 0),
								new Timestamp(Integer.parseInt(cbAno.getItemAt(cbAno.getSelectedIndex())),
										mes + cbMes.getSelectedIndex(),
										Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 0, 0, 0));
					} else if (rdbtnRealizadas.isSelected()) {
						bd.cargarReservasRealizadasUsuario(usuario,
								new Timestamp(Integer.parseInt(cbAno.getItemAt(cbAno.getSelectedIndex())),
										mes + cbMes.getSelectedIndex(),
										Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 0, 0, 0),
								new Timestamp(Integer.parseInt(cbAno.getItemAt(cbAno.getSelectedIndex())),
										mes + cbMes.getSelectedIndex(),
										Integer.parseInt(cbDia.getItemAt(cbDia.getSelectedIndex())), 0, 0, 0, 0));
					}else{
						//Cargar las reservas canceladas.
					}
				}
			});
			btnMostrarReserva.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnMostrarReserva.setBounds(130, 206, 147, 23);
		}
		return btnMostrarReserva;
	}

	private JButton getBtnAnularReserva() {
		if (btnAnularReserva == null) {
			btnAnularReserva = new JButton("Anular Reserva");
			btnAnularReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String horaInicio = (String) tbReservas.getValueAt(tbReservas.getSelectedRow(), 1);
					String horaFin = (String) tbReservas.getValueAt(tbReservas.getSelectedRow(), 2);
					bd.cancelarReservaUsuario(usuario, horaInicio, horaFin);
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
}
