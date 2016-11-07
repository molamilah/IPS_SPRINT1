package iguReserva;

import javax.swing.JDialog;

import logica.BaseDatos;
import logica.BaseDatos.ExcepcionPagoNoEncontrado;
import logica.BaseDatos.ExcepcionReservaNoEncontrada;
import logica.BaseDatos.ExcepcionUsuarioNoEncontrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaRegistrarPago extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseDatos bd;
	private JLabel lblDniDelUsuario;
	private JLabel lblFechaInicioReserva;
	private JTextField textFieldDNI;
	private JTextField textFieldFechaReserva;
	private JButton btnAtras;
	private JButton btnRegistarPago;
	
	public VentanaRegistrarPago() {
		setTitle("Registrar Pago");
		setBounds(100, 100, 550, 350);
		getContentPane().setLayout(null);
		getContentPane().add(getLblDniDelUsuario());
		getContentPane().add(getLblFechaInicioReserva());
		getContentPane().add(getTextFieldDNI());
		getContentPane().add(getTextFieldFechaReserva());
		getContentPane().add(getBtnAtras());
		getContentPane().add(getBtnRegistarPago());
		bd = new BaseDatos();
	}
	private JLabel getLblDniDelUsuario() {
		if (lblDniDelUsuario == null) {
			lblDniDelUsuario = new JLabel("DNI del Usuario:");
			lblDniDelUsuario.setBounds(40, 78, 92, 14);
		}
		return lblDniDelUsuario;
	}
	private JLabel getLblFechaInicioReserva() {
		if (lblFechaInicioReserva == null) {
			lblFechaInicioReserva = new JLabel("Fecha inicio reserva (a\u00F1o-mes-d\u00EDa hora:min:seg)");
			lblFechaInicioReserva.setBounds(166, 78, 294, 14);
		}
		return lblFechaInicioReserva;
	}
	private JTextField getTextFieldDNI() {
		if (textFieldDNI == null) {
			textFieldDNI = new JTextField();
			textFieldDNI.setBounds(40, 121, 86, 20);
			textFieldDNI.setColumns(10);
		}
		return textFieldDNI;
	}
	private JTextField getTextFieldFechaReserva() {
		if (textFieldFechaReserva == null) {
			textFieldFechaReserva = new JTextField();
			textFieldFechaReserva.setBounds(166, 121, 231, 20);
			textFieldFechaReserva.setColumns(10);
		}
		return textFieldFechaReserva;
	}
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atras");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAtras.setBounds(192, 215, 89, 23);
		}
		return btnAtras;
	}
	private JButton getBtnRegistarPago() {
		if (btnRegistarPago == null) {
			btnRegistarPago = new JButton("Registrar pago");
			btnRegistarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!(getTextFieldDNI().getText().equals("") || getTextFieldFechaReserva().getText() == null)) {
						String dni = getTextFieldDNI().getText();
						Timestamp fechaInicio = Timestamp.valueOf(getTextFieldFechaReserva().getText());
						try {
							bd.pasarPagoAPagado(dni, fechaInicio);
							JOptionPane.showMessageDialog(null, "El pago ha sido registrado con éxito", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (ExcepcionUsuarioNoEncontrado e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (ExcepcionReservaNoEncontrada e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (ExcepcionPagoNoEncontrado e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			btnRegistarPago.setBounds(291, 215, 120, 23);
		}
		return btnRegistarPago;
	}
}
