package iguAdministracion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import iguActividades.VentanaActividadesPropiasAdministracion;
import iguActividades.VentanaCreacionActividades;
import iguDisponibilidad.VentanaDisponibilidadInstalaciones;
import iguLogIn.LogIn;
import iguReserva.VentanaRegistrarPago;
import iguReserva.VentanaReservaAdministracion;
import iguReserva.VentanaReservasPropiasAdministracion;
import iguReserva.VentanaReservasPeriodicas;
import logica.Usuario;
import logica.BaseDatos;
import logica.BaseDatos.ExcepcionPagoNoEncontrado;
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
	private JPanel pnZonaAdministracion;
	private Usuario usuario;
	private JButton btnReservasPropias;
	private JButton btnLlegada;
	private JButton btnSalida;
	private JButton btnSimultanea;
	private JButton btnRegistrarPago;
	private JButton btnActualizarMensualidades;

	private BaseDatos bd = new BaseDatos();
	private JButton btnCrearActividad;
	private JButton btnBorrarSocio;

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
		contentPane.add(getPnZonaAdministracion());
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
					VentanaReservaAdministracion vra = new VentanaReservaAdministracion();
					vra.setModal(true);
					vra.setLocationRelativeTo(null);
					vra.setVisible(true);
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
			pnActividades.setLayout(new GridLayout(1, 1, 0, 0));
			pnActividades.add(getBtnCrearActividad());
			pnActividades.add(getBtnBorrarSocio());
		}
		return pnActividades;
	}

	private JPanel getPnZonaAdministracion() {
		if (pnZonaAdministracion == null) {
			pnZonaAdministracion = new JPanel();
			pnZonaAdministracion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
					"Zona Administraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnZonaAdministracion.setBounds(10, 336, 812, 140);
			pnZonaAdministracion.setLayout(new GridLayout(1, 1, 0, 0));
			pnZonaAdministracion.add(getBtnReservasPropias());
			pnZonaAdministracion.add(getBtnRegistrarPago());
			pnZonaAdministracion.add(getBtnActualizarMensualidades());
		}
		return pnZonaAdministracion;
	}

	private JButton getBtnReservasPropias() {
		if (btnReservasPropias == null) {
			btnReservasPropias = new JButton("Reservas Propias");
			btnReservasPropias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaReservasPropiasAdministracion vrpa = new VentanaReservasPropiasAdministracion(usuario);
					vrpa.setModal(true);
					vrpa.setLocationRelativeTo(null);
					vrpa.setVisible(true);
				}
			});
			btnReservasPropias.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnReservasPropias;
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

	private JButton getBtnRegistrarPago() {
		if (btnRegistrarPago == null) {
			btnRegistrarPago = new JButton("Registrar Pago");
			btnRegistrarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaRegistrarPago vrp = new VentanaRegistrarPago();
					vrp.setModal(true);
					vrp.setLocationRelativeTo(null);
					vrp.setVisible(true);
				}
			});
			btnRegistrarPago.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnRegistrarPago;
	}

	private JButton getBtnActualizarMensualidades() {
		if (btnActualizarMensualidades == null) {
			btnActualizarMensualidades = new JButton("Actualizar Mensualidades");
			btnActualizarMensualidades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						bd.actualizarMensualidades();
						JOptionPane.showMessageDialog(null, "Las mensualidades han sido actualizadas con éxito",
								"Informacion", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ExcepcionPagoNoEncontrado e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Informacion",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnActualizarMensualidades.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnActualizarMensualidades;
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("CrearActividad");
			btnCrearActividad.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaCreacionActividades va = new VentanaCreacionActividades();
					va.setLocationRelativeTo(null);
					va.setModal(true);
					va.setVisible(true);
				}
			});
		}
		return btnCrearActividad;
	}
	
		private JButton getBtnBorrarSocio() {
		if (btnBorrarSocio == null) {
			btnBorrarSocio = new JButton("Borrar Socio");
			btnBorrarSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaActividadesPropiasAdministracion vap = new VentanaActividadesPropiasAdministracion();
					vap.setModal(true);
					vap.setLocationRelativeTo(null);
					vap.setVisible(true);
				}
			});
			btnBorrarSocio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnBorrarSocio;
	}
}
