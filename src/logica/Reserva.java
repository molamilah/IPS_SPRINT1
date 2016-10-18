package logica;

import java.sql.Timestamp;

public class Reserva {

	private int id_reserva,id_usuario, id_sala;
	private Timestamp hora_inicio, hora_fin;
	private Timestamp hora_entrada, hora_salida;
	public Reserva(int id_reserva, int id_usuario, int id_sala, Timestamp hora_inicio, Timestamp hora_fin,
			Timestamp hora_entrada, Timestamp hora_salida) {
		super();
		this.id_reserva = id_reserva;
		this.id_usuario = id_usuario;
		this.id_sala = id_sala;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
		this.hora_entrada = hora_entrada;
		this.hora_salida = hora_salida;
	}
	public int getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public int getId_sala() {
		return id_sala;
	}
	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}
	public Timestamp getHora_inicio() {
		return hora_inicio;
	}
	public void setHora_inicio(Timestamp hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	public Timestamp getHora_fin() {
		return hora_fin;
	}
	public void setHora_fin(Timestamp hora_fin) {
		this.hora_fin = hora_fin;
	}
	public Timestamp getHora_entrada() {
		return hora_entrada;
	}
	public void setHora_entrada(Timestamp hora_entrada) {
		this.hora_entrada = hora_entrada;
	}
	public Timestamp getHora_salida() {
		return hora_salida;
	}
	public void setHora_salida(Timestamp hora_salida) {
		this.hora_salida = hora_salida;
	}

}
