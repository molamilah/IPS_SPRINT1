package iguDisponibilidad;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logica.BaseDatos;
import logica.Sala;
import logica.Usuario;

/**
 * @author DD
 *
 */
public class VentanaDisponibilidadInstalaciones extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblHorariosInstalaciones;
	private JButton btnAtras;
	private JComboBox<String> cbSalas;
	private JLabel lblSalas;
	private JComboBox<String> cbDia;
	private JComboBox<String> cbMes;
	private JLabel lblDia;
	private JLabel lblMes;
	private JButton btComprobar;

	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DATE);
	private int mes = c.get(Calendar.MONTH);
	private int año = c.get(Calendar.YEAR) - 1900;
	private JScrollPane scReservas;
	private JTable tablaReservas;
	private DefaultTableModel modeloTablaReservas;

	private BaseDatos bd;
	private List<Sala> salasGimnasio;
	Usuario usuario;
	
	String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

	@SuppressWarnings("serial")
	/**
	 * 
	 * @author Martin
	 * 
	 *         Clase para cambiar el color de las celdas de la table en funcion
	 *         de si estan o no reservadas, y de quien sea el propietario de la
	 *         reserva.
	 *
	 */
	class Celda extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (this.getText() == "Reservada") {
				this.setBackground(Color.RED);
			} else if (this.getText() == "Disponible") {
				this.setBackground(Color.GREEN);
			} else if (this.getText() == "Reserva Propia") {
				this.setBackground(Color.ORANGE);
			} else {
				this.setBackground(null);
				this.setIcon(null);
			}
			return this;
		}
	}

	@SuppressWarnings("serial")
	private JTable getTablaReservas() {
		if (tablaReservas == null) {
			modeloTablaReservas = new DefaultTableModel(new Object[] { "Hora Inicio", "Hora Fin", "Disponibilidad" },
					0);
			tablaReservas = new JTable(modeloTablaReservas) {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			Celda celda = new Celda();
			celda.setHorizontalAlignment(SwingConstants.CENTER);
			tablaReservas.setDefaultRenderer(Object.class, celda);
			tablaReservas.getTableHeader().setReorderingAllowed(false);
		}
		return tablaReservas;
	}

	public VentanaDisponibilidadInstalaciones(Usuario usuario) {
		this.usuario = usuario;
		bd = new BaseDatos();
		salasGimnasio = bd.cargarSalas();
		setResizable(false);
		setTitle("Disponiblidad de Instalaciones");
		setBounds(100, 100, 664, 552);
		getContentPane().setLayout(null);
		getContentPane().add(getCbMes());
		getContentPane().add(getLblHorariosInstalaciones());
		getContentPane().add(getBtnAtras());
		getContentPane().add(getCbSalas());
		getContentPane().add(getLblSalas());
		getContentPane().add(getCbDia());
		getContentPane().add(getLblDia());
		getContentPane().add(getLblMes());
		getContentPane().add(getBtComprobar());
		getContentPane().add(getScReservas());

		cbDia.setSelectedIndex(dia - 1);
		cbMes.setSelectedIndex(mes);
	}

	private JLabel getLblHorariosInstalaciones() {
		if (lblHorariosInstalaciones == null) {
			lblHorariosInstalaciones = new JLabel("Horarios Instalaciones");
			lblHorariosInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblHorariosInstalaciones.setBounds(192, 25, 263, 61);
		}
		return lblHorariosInstalaciones;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.setMnemonic('a');
			btnAtras.setToolTipText("Vuelve a la ventana principal");
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAtras.setBounds(547, 479, 89, 34);
		}
		return btnAtras;
	}

	private JComboBox<String> getCbSalas() {
		if (cbSalas == null) {
			cbSalas = new JComboBox<String>();
			cbSalas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					borrarModelo();
				}
			});
			int tamaño = salasGimnasio.size();
			String[] salas = new String[tamaño];
			for (int i = 0; i < salasGimnasio.size(); i++)
				salas[i] = salasGimnasio.get(i).getDescripcion();
			cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 14));
			cbSalas.setBounds(33, 120, 139, 30);
			cbSalas.setModel(new DefaultComboBoxModel<String>(salas));
		}
		return cbSalas;
	}

	private JLabel getLblSalas() {
		if (lblSalas == null) {
			lblSalas = new JLabel("Salas:");
			lblSalas.setDisplayedMnemonic('s');
			lblSalas.setLabelFor(getCbSalas());
			lblSalas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSalas.setBounds(33, 96, 67, 23);
		}
		return lblSalas;
	}

	private JComboBox<String> getCbDia() {
		if (cbDia == null) {
			String[] dias = calcularDiasMes();
			cbDia = new JComboBox<String>();
			cbDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarModelo();
				}
			});
			cbDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
			cbDia.setBounds(182, 121, 88, 29);
			cbDia.setModel(new DefaultComboBoxModel<String>(dias));
		}
		return cbDia;
	}

	/**
	 * Metodo que invoca al metodo que calcula el numero de dias por mes
	 */
	private void cambiarNumeroDias() {
		int indice = cbDia.getSelectedIndex();
		String[] dias = calcularDiasMes();
		cbDia.setModel(new DefaultComboBoxModel<String>(dias));
		if (indice < dias.length)
			cbDia.setSelectedIndex(indice);
		else
			cbDia.setSelectedIndex(dias.length - 1);
	}

	/**
	 * Metodo para calcular el numero de dias que tiene un mes.
	 * 
	 * @return numero de dias por mes.
	 */
	private String[] calcularDiasMes() {
		String[] dias;
		String aux = String.valueOf(cbMes.getSelectedItem());
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

	private JComboBox<String> getCbMes() {
		if (cbMes == null) {			
			cbMes = new JComboBox<String>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cambiarNumeroDias();
				}
			});
			cbMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
			cbMes.setModel(new DefaultComboBoxModel<String>(meses));
			cbMes.setBounds(280, 120, 113, 28);
		}
		return cbMes;
	}

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Dia:");
			lblDia.setLabelFor(getCbDia());
			lblDia.setDisplayedMnemonic('d');
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblDia.setBounds(181, 97, 45, 23);
		}
		return lblDia;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes:");
			lblMes.setDisplayedMnemonic('m');
			lblMes.setLabelFor(getCbMes());
			lblMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMes.setBounds(280, 97, 45, 23);
		}
		return lblMes;
	}

	private JButton getBtComprobar() {
		if (btComprobar == null) {
			btComprobar = new JButton("Comprobar");
			btComprobar.setToolTipText("Muestra todas las horas disponibles de una sala");
			btComprobar.setMnemonic('C');
			btComprobar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btComprobar.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					borrarModelo();
					salasGimnasio = bd
							.cargarDisponibilidadSalaSeleccionada(salasGimnasio.get(cbSalas.getSelectedIndex()).getId_sala());
					Sala sala = salasGimnasio.get(0);
					int dia = Integer.valueOf(cbDia.getSelectedIndex()) + 1;
					int month = Integer.valueOf(cbMes.getSelectedIndex());
					Timestamp inicio = new Timestamp(año, month, dia, 0, 0, 0, 0);
					Usuario[] horasReservadas = sala.reservasDia(inicio);
					for (int i = 0; i < horasReservadas.length; i++) {
						if (horasReservadas[i] == null) {
							modeloTablaReservas
									.addRow(new Object[] { "" + (i + 0) + ":00", (i + 0) + ":59", "Disponible" });
						} else if (usuario.getId_usuario() == (horasReservadas[i].getId_usuario())) {
							modeloTablaReservas
									.addRow(new Object[] { "" + (i + 0) + ":00", (i + 0) + ":59", "Reserva Propia" });
						} else {
							modeloTablaReservas
									.addRow(new Object[] { "" + (i + 0) + ":00", (i + 0) + ":59", "Reservada" });
						}
					}
					salasGimnasio = bd.cargarSalas();
				}
			});
			btComprobar.setBounds(453, 120, 119, 28);
		}
		return btComprobar;
	}

	/**
	 * Borra los elementos de la tabla
	 */
	private void borrarModelo() {
		while (modeloTablaReservas.getRowCount() > 0) {
			modeloTablaReservas.removeRow(0);
		}
	}

	private JScrollPane getScReservas() {
		if (scReservas == null) {
			scReservas = new JScrollPane();
			scReservas.setBounds(33, 161, 603, 307);
			scReservas.setViewportView(getTablaReservas());
		}
		return scReservas;
	}
}