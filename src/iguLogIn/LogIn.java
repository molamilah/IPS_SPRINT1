package iguLogIn;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import iguAdministracion.VentanaPrincipalAdministracion;
import iguUsuario.VentanaPrincipalUsuarios;
import logica.BaseDatos;
import logica.BaseDatos.ExcepcionUsuarioNoEncontrado;
import logica.BaseDatos.ExceptionUsuarioContraseña;
import logica.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnLogIn;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JTextField txUsuario;
	private JPasswordField pfContraseña;
	private JButton btEntrar;
	private JLabel lbImagen;
	private JButton btnCancelar;
	BaseDatos bd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogIn() {
		bd = new BaseDatos();
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogIn.class.getResource("/img/img-recepcion-2.jpg")));
		setResizable(false);
		setTitle("LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 792, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPnLogIn());
		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(btEntrar);
	}

	private JPanel getPnLogIn() {
		if (pnLogIn == null) {
			pnLogIn = new JPanel();
			pnLogIn.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			pnLogIn.setBounds(52, 43, 673, 424);
			pnLogIn.setLayout(null);
			pnLogIn.add(getLblUsuario());
			pnLogIn.add(getLblContrasea());
			pnLogIn.add(getTxUsuario());
			pnLogIn.add(getPfContraseña());
			pnLogIn.add(getBtEntrar());
			pnLogIn.add(getLbImagen());
			pnLogIn.add(getBtnCancelar());
		}
		return pnLogIn;
	}

	private JLabel getLblUsuario() {
		if (lblUsuario == null) {
			lblUsuario = new JLabel("Usuario:");
			lblUsuario.setDisplayedMnemonic('U');
			lblUsuario.setLabelFor(getTxUsuario());
			lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblUsuario.setBounds(56, 103, 59, 47);
		}
		return lblUsuario;
	}

	private JLabel getLblContrasea() {
		if (lblContrasea == null) {
			lblContrasea = new JLabel("Contrase\u00F1a:");
			lblContrasea.setLabelFor(getPfContraseña());
			lblContrasea.setDisplayedMnemonic('C');
			lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblContrasea.setBounds(56, 187, 89, 29);
		}
		return lblContrasea;
	}

	private JTextField getTxUsuario() {
		if (txUsuario == null) {
			txUsuario = new JTextField();
			txUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txUsuario.setBounds(135, 125, 251, 32);
			txUsuario.setColumns(10);
		}
		return txUsuario;
	}

	private JPasswordField getPfContraseña() {
		if (pfContraseña == null) {
			pfContraseña = new JPasswordField();
			pfContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
			pfContraseña.setBounds(135, 187, 251, 32);
		}
		return pfContraseña;
	}

	private JButton getBtEntrar() {
		if (btEntrar == null) {
			btEntrar = new JButton("Entrar");
			btEntrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String password = new String(pfContraseña.getPassword()).toUpperCase();
					String user = txUsuario.getText().toUpperCase();
					try {
						if (user.equals("") || password.equals("")) {
							JOptionPane.showMessageDialog(null,
									"Los campos usuario y contraseña han de rellenarse obligatoriamente.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else if (user.equals("ADMIN")) {
							if (bd.comprobarUserPassword(user, password)) {
								dispose();
								VentanaPrincipalAdministracion vp = new VentanaPrincipalAdministracion(new Usuario(user));
								vp.setLocationRelativeTo(null);
								vp.setVisible(true);
							}

						} else if (user.equals("CONTABLE")) {
							if (bd.comprobarUserPassword(user, password)) {
								dispose();
								VentanaPrincipalAdministracion vp = new VentanaPrincipalAdministracion(new Usuario(user));
								vp.setLocationRelativeTo(null);
								vp.setVisible(true);
							}

						} else {
							if (bd.comprobarUserPassword(user, password)) {
								dispose();
								VentanaPrincipalUsuarios vpu = new VentanaPrincipalUsuarios(new Usuario(user));
								vpu.setLocationRelativeTo(null);
								vpu.setVisible(true);
							}

						}
					}catch (ExceptionUsuarioContraseña e) {
						JOptionPane.showMessageDialog(null, "El usuario y la contraseña no se corresponden.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}catch(ExcepcionUsuarioNoEncontrado e1){
						JOptionPane.showMessageDialog(null, "El usuario no existe en la base de datos.", "ERROR",
								JOptionPane.ERROR_MESSAGE);						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btEntrar.setMnemonic('E');
			btEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btEntrar.setBounds(280, 248, 108, 29);
		}
		return btEntrar;
	}

	private JLabel getLbImagen() {
		if (lbImagen == null) {
			lbImagen = new JLabel("");
			lbImagen.setIcon(new ImageIcon(LogIn.class.getResource("/img/img-recepcion-reducida.jpg")));
			lbImagen.setBounds(387, 75, 276, 209);
		}
		return lbImagen;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setMnemonic('C');
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnCancelar.setBounds(156, 248, 106, 28);
		}
		return btnCancelar;
	}
}
