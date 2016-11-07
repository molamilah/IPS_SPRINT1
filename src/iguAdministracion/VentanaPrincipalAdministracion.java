package iguAdministracion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import iguDisponibilidad.VentanaDisponibilidadInstalaciones;
import iguLogIn.LogIn;
import iguReserva.VentanaReservaAdministracion;
import iguReserva.VentanaReservasPropiasAdministracion;
import logica.Usuario;

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

	/**
	 * Create the frame.
	 * @param usuario 
	 */
	public VentanaPrincipalAdministracion(Usuario usuario) {
		this.usuario = usuario;
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipalAdministracion.class.getResource("/img/img-recepcion-2.jpg")));
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
			pnInstalaciones.setBorder(new TitledBorder(null, "Instalaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnInstalaciones.setBounds(10, 22, 812, 146);
			pnInstalaciones.setLayout(new GridLayout(1, 1, 0, 0));
			pnInstalaciones.add(getBtDisponibilidad());
			pnInstalaciones.add(getBtnReservas());
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
			btnReservas = new JButton("Reservas");
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
			pnActividades.setBorder(new TitledBorder(null, "Actividades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnActividades.setBounds(10, 179, 812, 146);
		}
		return pnActividades;
	}
	private JPanel getPnZonaAdministracion() {
		if (pnZonaAdministracion == null) {
			pnZonaAdministracion = new JPanel();
			pnZonaAdministracion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Zona Administraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnZonaAdministracion.setBounds(10, 336, 812, 140);
			pnZonaAdministracion.setLayout(new GridLayout(0, 1, 0, 0));
			pnZonaAdministracion.add(getBtnReservasPropias());
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
}
