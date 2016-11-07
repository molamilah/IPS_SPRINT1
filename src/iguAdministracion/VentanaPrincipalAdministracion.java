package iguAdministracion;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import iguDisponibilidad.VentanaDisponibilidadInstalaciones;
import iguLogIn.LogIn;
import iguReserva.VentanaReservasPeriodicas;
import logica.Usuario;
import ocupacion.OcupacionSalas;

public class VentanaPrincipalAdministracion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnInstalaciones;
	private JButton btDisponibilidad;
	private JButton btnReservas;
	private JButton btnSalir;
	private JPanel pnActividades;
	private JPanel pnCursillos;
	private Usuario usuario;
	private JButton btnLlegada;
	private JButton btnSalida;
	private JButton btnSimultanea;

	/**
	 * Create the frame.
	 * 
	 * @param usuario
	 */
	public VentanaPrincipalAdministracion(Usuario usuario) {
		setResizable(false);
		this.usuario = usuario;
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaPrincipalAdministracion.class.getResource("/img/img-recepcion-2.jpg")));
		setTitle("Administraci\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPnInstalaciones());
		contentPane.add(getBtnSalir());
		contentPane.add(getPnActividades());
		contentPane.add(getPnCursillos());
	}

	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel();
			pnInstalaciones.setBorder(
					new TitledBorder(null, "Instalaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnInstalaciones.setBounds(10, 22, 812, 146);
			pnInstalaciones.setLayout(new GridLayout(1, 1, 0, 0));
			pnInstalaciones.add(getBtnLlegada());
			pnInstalaciones.add(getBtnSalida());
			pnInstalaciones.add(getBtDisponibilidad());
			pnInstalaciones.add(getBtnReservas());
			pnInstalaciones.add(getBtnSimultanea());
		}
		return pnInstalaciones;
	}

	private JButton getBtDisponibilidad() {
		if (btDisponibilidad == null) {
			btDisponibilidad = new JButton("Disponibilidad");
			btDisponibilidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaDisponibilidadInstalaciones vdi = new VentanaDisponibilidadInstalaciones(usuario);
					vdi.setModal(true);
					vdi.setLocationRelativeTo(null);
					vdi.setVisible(true);

				}
			});
			btDisponibilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btDisponibilidad.setMnemonic('D');
		}
		return btDisponibilidad;
	}

	private JButton getBtnReservas() {
		if (btnReservas == null) {
			btnReservas = new JButton("Reserva Unica");
			btnReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

				}
			});
			btnReservas.setMnemonic('R');
			btnReservas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnReservas;
	}

	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.setMnemonic('S');
			btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					LogIn l = new LogIn();
					l.setVisible(true);
				}
			});
			btnSalir.setBounds(712, 479, 110, 65);
		}
		return btnSalir;
	}

	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setBorder(
					new TitledBorder(null, "Actividades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnActividades.setBounds(10, 179, 812, 146);
		}
		return pnActividades;
	}

	private JPanel getPnCursillos() {
		if (pnCursillos == null) {
			pnCursillos = new JPanel();
			pnCursillos
					.setBorder(new TitledBorder(null, "Cursillos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnCursillos.setBounds(10, 336, 812, 140);
		}
		return pnCursillos;
	}

	private JButton getBtnLlegada() {
		if (btnLlegada == null) {
			btnLlegada = new JButton("Indicar Llegada");
			btnLlegada.setMnemonic('L');
			btnLlegada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String respuesta = JOptionPane.showInputDialog(contentPane,
							"Introduzca el dni del usuario que ha llegado");
					if (respuesta != null) {
						if (respuesta.equals(""))
							JOptionPane.showMessageDialog(contentPane, "No se ha indicado ningun cliente");
						else {
							boolean estado = OcupacionSalas.indicarLlegada(respuesta);
							if (!estado)
								JOptionPane.showMessageDialog(contentPane,
										"El ciente con el DNI indicado no tiene una reserva y no puede pasar a usar las instalaciones");
							else
								JOptionPane.showMessageDialog(contentPane, "La llegada ha sido indicada correctamente");
						}
					}
				}
			});
			btnLlegada.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnLlegada;
	}

	private JButton getBtnSalida() {
		if (btnSalida == null) {
			btnSalida = new JButton("Indicar Salida");
			btnSalida.setMnemonic('S');
			btnSalida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String respuesta = JOptionPane.showInputDialog(contentPane,
							"Introduzca el dni del usuario que se va");
					if (respuesta != null) {
						if (respuesta.equals(""))
							JOptionPane.showMessageDialog(contentPane, "No se ha indicado ningun cliente");

						else {
							boolean estado = OcupacionSalas.indicarSalida(respuesta);
							if (!estado)
								JOptionPane.showMessageDialog(contentPane,
										"No se ha podido encontrar ningun cliente usando las instalaciones con ese DNI");
							else
								JOptionPane.showMessageDialog(contentPane, "La salida ha sido indicada correctamente");
						}
					}
				}
			});
			btnSalida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnSalida;
	}

	private JButton getBtnSimultanea() {
		if (btnSimultanea == null) {
			btnSimultanea = new JButton("Reserva periodica");
			btnSimultanea.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaReservasPeriodicas vrp = new VentanaReservasPeriodicas();
					vrp.setModal(true);
					vrp.setLocationRelativeTo(null);
					vrp.setVisible(true);
				}
			});
			btnSimultanea.setMnemonic('P');
			btnSimultanea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnSimultanea;
	}
}
