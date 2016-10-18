package logica;

import java.sql.Timestamp;
import java.util.List;

public class Sala {

	private int id_sala;
	private String descripcion;
	private double precio;
	private boolean ocupada;
	private List<Reserva> reservas;

	public Sala(int id_sala, String descripcion, double precio,List<Reserva> reservas) {
		super();
		this.id_sala = id_sala;
		this.descripcion = descripcion;
		this.precio = precio;
		this.reservas = reservas;
	}

	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@SuppressWarnings("deprecation")
	public Usuario[] reservasDia(Timestamp inicio) {
		Usuario[] propietarioReserva = new Usuario[24];
		for (int i = 0; i < propietarioReserva.length; i++)
			propietarioReserva[i] = null;
		int dia = inicio.getDate() + 1;
		int mes = inicio.getMonth();
		for (Reserva reserva : reservas) {
			int diaReserva = reserva.getHora_inicio().getDate() + 1;
			int mesReserva = reserva.getHora_inicio().getMonth();
			int inicioReserva = reserva.getHora_inicio().getHours();
			int finReserva = reserva.getHora_fin().getHours();
			int duracion = finReserva - inicioReserva;
			if (diaReserva == dia && mes == mesReserva) {
				for (int i = 0; i < duracion; i++) {
					propietarioReserva[inicioReserva + i] = new Usuario(reserva.getId_usuario());
				}
			}
		}
		return  propietarioReserva;
	}
}
