package iguActividades;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.BBDDReservasActividades;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VentanaCreacionActividades extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JTextField txtNombre;
	private JLabel lblTitulo;
	private JButton btnCancelar;
	private JLabel lblPlazas;
	private JButton btnCrearUnica;
	private JSpinner spPlazas;
	private JButton btnCrearPeriodica;

	/**
	 * Create the dialog.
	 */
	public VentanaCreacionActividades() {
		setTitle("Creacion de actividades");
		setBounds(100, 100, 632, 438);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblNombre());
		contentPanel.add(getLblDescripcion());
		contentPanel.add(getTxtDescripcion());
		contentPanel.add(getTxtNombre());
		contentPanel.add(getLblTitulo());
		contentPanel.add(getLblPlazas());
		contentPanel.add(getBtnCancelar());
		contentPanel.add(getBtnCrearUnica());
		contentPanel.add(getSpPlazas());
		contentPanel.add(getBtnCrearPeriodica());
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre de la actividad");
			lblNombre.setLabelFor(getTxtNombre());
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNombre.setBounds(71, 87, 157, 19);
		}
		return lblNombre;
	}

	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel("Descripcion de la actividad");
			lblDescripcion.setLabelFor(getTxtDescripcion());
			lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDescripcion.setBounds(221, 175, 178, 28);
		}
		return lblDescripcion;
	}

	private JTextArea getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextArea();
			txtDescripcion.setWrapStyleWord(true);
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setBounds(71, 214, 488, 120);
		}
		return txtDescripcion;
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(303, 86, 256, 20);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Ventana para la creacion de actividades");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitulo.setBounds(119, 21, 374, 39);
		}
		return lblTitulo;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar creacion");
			btnCancelar.setBounds(26, 354, 165, 23);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}

	private JLabel getLblPlazas() {
		if (lblPlazas == null) {
			lblPlazas = new JLabel("Limite de plazas");
			lblPlazas.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblPlazas.setBounds(134, 133, 117, 19);
		}
		return lblPlazas;
	}

	private JButton getBtnCrearUnica() {
		if (btnCrearUnica == null) {
			btnCrearUnica = new JButton("Crear Actividad Unica");
			btnCrearUnica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtNombre.getText().isEmpty())
						JOptionPane.showMessageDialog(getContentPane(),
								"El Nombre de la actividad no puede quedar vacio");
					else if ((Integer) spPlazas.getValue() <= 0)
						JOptionPane.showMessageDialog(getContentPane(), "El numero de plazas debe ser como minimo 1");
					else {
						BBDDReservasActividades.conectar();
						if (BBDDReservasActividades.comprobarActividad(txtNombre.getText())) {
							VentanaActividadUnica vr = new VentanaActividadUnica(new Object[] { txtNombre.getText(),
									txtDescripcion.getText(), spPlazas.getValue() });
							vr.setLocationRelativeTo(null);
							vr.setModal(true);
							vr.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(getContentPane(),
									"Ya existe una actividad con ese nombre en la base de datos");
							BBDDReservasActividades.desconectar();
						}
					}
				}
			});
			btnCrearUnica.setBounds(201, 354, 165, 23);
		}
		return btnCrearUnica;
	}

	private JSpinner getSpPlazas() {
		if (spPlazas == null) {
			spPlazas = new JSpinner();
			spPlazas.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spPlazas.setBounds(303, 132, 53, 20);
		}
		return spPlazas;
	}

	private JButton getBtnCrearPeriodica() {
		if (btnCrearPeriodica == null) {
			btnCrearPeriodica = new JButton("Crear Actividad Periodica");
			btnCrearPeriodica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtNombre.getText().isEmpty())
						JOptionPane.showMessageDialog(getContentPane(),
								"El Nombre de la actividad no puede quedar vacio");
					else if ((Integer) spPlazas.getValue() <= 0)
						JOptionPane.showMessageDialog(getContentPane(), "El numero de plazas debe ser como minimo 1");
					else {
						BBDDReservasActividades.conectar();
						if (BBDDReservasActividades.comprobarActividad(txtNombre.getText())) {
							VentanaActividadesPeriodicas vp = new VentanaActividadesPeriodicas(new Object[] { txtNombre.getText(),
									txtDescripcion.getText(), spPlazas.getValue() });
							vp.setLocationRelativeTo(null);
							vp.setModal(true);
							vp.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(getContentPane(),
									"Ya existe una actividad con ese nombre en la base de datos");
							BBDDReservasActividades.desconectar();
						}
					}
				}
			});
			btnCrearPeriodica.setBounds(376, 354, 183, 23);
		}
		return btnCrearPeriodica;
	}
}
