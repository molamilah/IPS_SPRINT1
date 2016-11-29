package iguActividades;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import logica.BaseDatos;
import logica.Usuario;

public class VentanaActividadesPropias extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btnAtras;
	private JScrollPane scrollPane;
	private JButton btnCancelarActividad;
	private Usuario usuario;
	private BaseDatos bd;
	private JTable tbActividades;

	private DefaultTableModel modeloTablaReservas;

	/**
	 * Create the dialog.
	 */
	public VentanaActividadesPropias(Usuario usuario) {
		this.usuario = usuario;
		this.bd = new BaseDatos();
		setTitle("Actividades Propias");
		setBounds(100, 100, 600, 427);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnAtras());
		getContentPane().add(getScrollPane());
		getContentPane().add(getBtnCancelarActividad());

		cargarTabla();
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.setBounds(496, 362, 88, 27);
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btnAtras;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 46, 584, 316);
			scrollPane.setViewportView(getTbActividades());
		}
		return scrollPane;
	}

	private JButton getBtnCancelarActividad() {
		if (btnCancelarActividad == null) {
			btnCancelarActividad = new JButton("Cancelar Actividad");
			btnCancelarActividad.setEnabled(false);
			btnCancelarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// lamada a un metodo de la base de datos que borra al
					// usuario de la lista de la actividad.
					int id_actividad = bd.encontrarIdActividad(
							tbActividades.getValueAt(tbActividades.getSelectedRow(), 0).toString());
					bd.borrarReservaActividad(usuario.getId_usuario(), id_actividad);
					modeloTablaReservas.removeRow(tbActividades.getSelectedRow());
					JOptionPane.showMessageDialog(null, "Su reserva de actividad se ha borrado con exito.");
					if (modeloTablaReservas.getRowCount() < 1) {
						btnCancelarActividad.setEnabled(false);
					}
				}
			});
			btnCancelarActividad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCancelarActividad.setBounds(213, 12, 160, 23);
		}
		return btnCancelarActividad;
	}

	private void cargarTabla() {
		List<String> lista = bd.cargarNombreDescripcionesActividades(usuario.getId_usuario());
		for (String s : lista) {
			String[] datos = s.split("-");
			modeloTablaReservas.addRow(new Object[] { datos[0], datos[1] });
		}
	}

	private JTable getTbActividades() {
		if (tbActividades == null) {
			modeloTablaReservas = new DefaultTableModel(new Object[] { "Nombre", "Descripcion" }, 0);
			tbActividades = new JTable(modeloTablaReservas);
			tbActividades.setBounds(0, 0, 1, 1);
			tbActividades.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btnCancelarActividad.setEnabled(true);
				}
			});
		}
		return tbActividades;
	}
}
