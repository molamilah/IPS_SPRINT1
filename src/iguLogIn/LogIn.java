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
import logica.BaseDatos.ExcepcionUserNotFound;
import logica.BaseDatos.ExceptionUserPassword;
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
	private JLabel lblPassword;
	private JTextField txUsuario;
	private JPasswordField pfPassword;
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
			pnLogIn.add(getpfPassword());
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
		if (lblPassword == null) {
			lblPassword = new JLabel("Contrase\u00F1a:");
			lblPassword.setLabelFor(getpfPassword());
			lblPassword.setDisplayedMnemonic('C');
			lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblPassword.setBounds(56, 187, 89, 29);
		}
		return lblPassword;
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

	private JPasswordField getpfPassword() {
		if (pfPassword == null) {
			pfPassword = new JPasswordField();
			pfPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
			pfPassword.setBounds(135, 187, 251, 32);
		}
		return pfPassword;
	}

	private JButton getBtEntrar() {
		if (btEntrar == null) {
			btEntrar = new JButton("Entrar");
			btEntrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String password = new String(pfPassword.getPassword()).toUpperCase();
					String user = txUsuario.getText().toUpperCase();
					try {
						if (user.equals("") || password.equals("")) {
							JOptionPane.showMessageDialog(null,
									"Los campos usuario y contrase\u00F1a han de rellenarse obligatoriamente.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else if (user.equals("ADMIN")) {
							if (bd.comprobarUserPasswordAdmin(user, password)) {
								dispose();
								VentanaPrincipalAdministracion vp = new VentanaPrincipalAdministracion(
										bd.cargarAdministrador(user));
								vp.setLocationRelativeTo(null);
								vp.setVisible(true);
							}
						} else if (user.equals("CONTABLE")) {
							if (bd.comprobarUserPasswordAdmin(user, password)) {
								dispose();
								VentanaPrincipalAdministracion vp = new VentanaPrincipalAdministracion(
										bd.cargarAdministrador(user));
								vp.setLocationRelativeTo(null);
								vp.setVisible(true);
							}
						} else {
							if (bd.comprobarUserPassword(Integer.parseInt(user), password)) {
								dispose();
								VentanaPrincipalUsuarios vpu = new VentanaPrincipalUsuarios(
										bd.cargarUsuario(Integer.parseInt(user)));
								vpu.setLocationRelativeTo(null);
								vpu.setVisible(true);
							}
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(contentPane,
								"El usuario debe ser num\u00E9rico");
					} catch (ExceptionUserPassword e) {
						JOptionPane.showMessageDialog(null, "El usuario y la contrase\u00F1a no se corresponden.",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					} catch (ExcepcionUserNotFound e1) {
						JOptionPane.showMessageDialog(null, "El usuario no existe en la base de datos.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
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
