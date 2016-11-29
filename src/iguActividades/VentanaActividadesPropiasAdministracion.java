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
import logica.BaseDatos.ExcepcionUsuarioNoEncontrado;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class VentanaActividadesPropiasAdministracion extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAtras;
	private JScrollPane scrollPane;
	private JButton btnCancelarActividad;
	private Usuario usuario;
	private BaseDatos bd;
	private JTable tbActividades;

	private DefaultTableModel modeloTablaReservas;
	private JButton btCargarActividades;
	private JTextField txIdUsuario;
	private JLabel lblIdUsuario;

	/**
	 * Create the dialog.
	 */
	public VentanaActividadesPropiasAdministracion() {
		this.bd = new BaseDatos();
		setTitle("Actividades Usuario");
		setBounds(100, 100, 600, 427);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnAtras());
		getContentPane().add(getScrollPane());
		getContentPane().add(getBtnCancelarActividad());
		getContentPane().add(getBtCargarActividades());
		getContentPane().add(getTxIdUsuario());
		getContentPane().add(getLblIdUsuario());
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
			btnCancelarActividad.setBounds(414, 11, 160, 23);
		}
		return btnCancelarActividad;
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

	private JButton getBtCargarActividades() {
		if (btCargarActividades == null) {
			btCargarActividades = new JButton("Cargar Actividades");
			btCargarActividades.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btCargarActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					borrarModelo();
					try {
						propietarioReserva();
						cargarTabla();
					} catch (NullPointerException x) {
						JOptionPane.showMessageDialog(null, "Es obligatorio introducir el id del socio.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btCargarActividades.setBounds(222, 11, 182, 23);
		}
		return btCargarActividades;
	}

	private void cargarTabla() {
		List<String> lista = bd.cargarNombreDescripcionesActividades(usuario.getId_usuario());
		for (String s : lista) {
			String[] datos = s.split("-");
			modeloTablaReservas.addRow(new Object[] { datos[0], datos[1] });
		}
	}

	/**
	 * Metodo que devuelve el usuario sobre el que se van a realizar las labora
	 * de cancelacion de las reservas.
	 * 
	 * @return
	 */
	private Usuario propietarioReserva() {
		try {
			if (!txIdUsuario.getText().equals("")) {
				if (bd.comprobarUsuario(Integer.parseInt(txIdUsuario.getText()))) {
					this.usuario = bd.cargarUsuario(Integer.parseInt(txIdUsuario.getText()));
					return this.usuario;
				}
			}
		} catch (ExcepcionUsuarioNoEncontrado e) {
			JOptionPane.showMessageDialog(null, "El usuario no existe en la base de datos.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * Borra los elementos de la tabla
	 */
	private void borrarModelo() {
		while (modeloTablaReservas.getRowCount() > 0) {
			modeloTablaReservas.removeRow(0);
		}
	}

	private JTextField getTxIdUsuario() {
		if (txIdUsuario == null) {
			txIdUsuario = new JTextField();
			txIdUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txIdUsuario.setBounds(92, 14, 106, 20);
			txIdUsuario.setColumns(10);
		}
		return txIdUsuario;
	}

	private JLabel getLblIdUsuario() {
		if (lblIdUsuario == null) {
			lblIdUsuario = new JLabel("ID Usuario:");
			lblIdUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblIdUsuario.setBounds(10, 15, 72, 20);
		}
		return lblIdUsuario;
	}
}
