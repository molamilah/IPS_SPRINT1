package iguConflictos;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logica.BBDDReservasActividades;
import logica.Reserva;

import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class VentanaConflictos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Reserva> conflictos;
	private JButton btnUpdate;
	private JButton btnCancel;
	private JTextArea txtInfo;
	private JTable table;
	private JPanel pnTabla;
	private JScrollPane scrollPane;
	DefaultTableModel modelo;

	ArrayList<Object[]> reservas;

	/**
	 * Create the frame.
	 */
	public VentanaConflictos(ArrayList<Reserva> conflictos, ArrayList<Object[]> reservas) {
		this.reservas = reservas;
		this.conflictos = conflictos;
		modelo = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		setTitle("Conflictos Encontrados");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaConflictos.class.getResource("/img/img-recepcion-reducida.jpg")));
		setResizable(false);
		setBounds(100, 100, 811, 451);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnUpdate());
		getContentPane().add(getBtnCancel());
		getContentPane().add(getTxtInfo());
		getContentPane().add(getPnTabla());

	}

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("Reservar y Sustituir");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					BBDDReservasActividades.resolverConflictos(conflictos, reservas);
					JOptionPane.showMessageDialog(getContentPane(), "Reservas actualizadas correctamente");
					dispose();
				}
			});
			btnUpdate.setBounds(479, 365, 214, 29);
		}
		return btnUpdate;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancelar y reservar otra fecha");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(getContentPane(), "Volviendo a la pantalla anterior sin reservar");
					dispose();
				}
			});
			btnCancel.setBounds(81, 365, 221, 28);
		}
		return btnCancel;
	}

	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setLineWrap(true);
			txtInfo.setWrapStyleWord(true);
			txtInfo.setText(
					"La reserva o reservas que deseas hacer han encontrado conflictos con otras reservas existentes que se muestran a continuacion");
			txtInfo.setEditable(false);
			txtInfo.setBounds(38, 33, 709, 77);
		}
		return txtInfo;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(modelo);
			rellenarTabla();
			table.getColumnModel().getColumn(0).setMinWidth(100);
			table.getColumnModel().getColumn(1).setMinWidth(100);
			table.getColumnModel().getColumn(2).setMaxWidth(50);
			table.getColumnModel().getColumn(4).setMinWidth(200);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(0).setCellRenderer(tcr);
			table.getColumnModel().getColumn(1).setCellRenderer(tcr);
			table.getColumnModel().getColumn(2).setCellRenderer(tcr);
			table.getColumnModel().getColumn(3).setCellRenderer(tcr);
			table.getColumnModel().getColumn(4).setCellRenderer(tcr);
		}
		return table;
	}

	private void rellenarTabla() {
		modelo.addColumn("FechaInicio");
		modelo.addColumn("FechaFin");
		modelo.addColumn("IdSala");
		modelo.addColumn("Cliente");
		modelo.addColumn("Direccion");
		for (Reserva r : conflictos) {
			String[] datosCliente = BBDDReservasActividades.ObtenerDatosCliente(r.getId_usuario());
			modelo.addRow(new Object[] { r.getHora_inicio(), r.getHora_fin(), r.getId_sala(), datosCliente[0],
					datosCliente[1] });
		}
	}

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setBounds(38, 143, 709, 186);
			pnTabla.setLayout(new GridLayout(0, 1, 0, 0));
			pnTabla.add(getScrollPane());
		}
		return pnTabla;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
}
