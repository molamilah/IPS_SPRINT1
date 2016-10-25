package iguUsuario;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import iguAdministracion.VentanaPrincipalAdministracion;
import iguDisponibilidad.VentanaDisponibilidadInstalaciones;
import iguLogIn.LogIn;
import iguReserva.VentanaReservaUsuario;
import logica.Usuario;
import javax.swing.UIManager;
import java.awt.Color;

public class VentanaPrincipalUsuarios extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnInstalaciones;
	private JButton btnNewButton;
	private JButton btnReservar;
	private JButton btnSalir;
	private JPanel pnActividades;
	private JPanel pnZonaUsuario;
	private Usuario usuario;
	private JButton btnReservasPropias;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipalUsuarios(Usuario usuario) {
		this.usuario = usuario;
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipalAdministracion.class.getResource("/img/img-recepcion-2.jpg")));
		setTitle("Socio:"+usuario.getNombre());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPnInstalaciones());
		contentPane.add(getBtnSalir());
		contentPane.add(getPnActividades());
		contentPane.add(getPnZonaUsuario());
	}
	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel();
			pnInstalaciones.setBorder(new TitledBorder(null, "Instalaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnInstalaciones.setBounds(10, 22, 812, 146);
			pnInstalaciones.setLayout(new GridLayout(1, 1, 0, 0));
			pnInstalaciones.add(getBtnNewButton());
			pnInstalaciones.add(getBtnReservar());
		}
		return pnInstalaciones;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Disponibilidad");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaDisponibilidadInstalaciones vdi = new VentanaDisponibilidadInstalaciones(usuario);
					vdi.setModal(true);
					vdi.setLocationRelativeTo(null);
					vdi.setVisible(true);
					
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnNewButton.setMnemonic('D');
		}
		return btnNewButton;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaReservaUsuario vru = new VentanaReservaUsuario(usuario);
					vru.setModal(true);
					vru.setLocationRelativeTo(null);
					vru.setVisible(true);
				}
			});
			btnReservar.setMnemonic('R');
			btnReservar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnReservar;
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
	private JPanel getPnZonaUsuario() {
		if (pnZonaUsuario == null) {
			pnZonaUsuario = new JPanel();
			pnZonaUsuario.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Zona Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnZonaUsuario.setBounds(10, 336, 812, 140);
			pnZonaUsuario.setLayout(new GridLayout(1, 0, 0, 0));
			pnZonaUsuario.add(getBtnReservasPropias());
		}
		return pnZonaUsuario;
	}
	private JButton getBtnReservasPropias() {
		if (btnReservasPropias == null) {
			btnReservasPropias = new JButton("Reservas Propias");
			btnReservasPropias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnReservasPropias.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return btnReservasPropias;
	}
}
