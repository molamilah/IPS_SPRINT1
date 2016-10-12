package logica;

import java.sql.Timestamp;

public class Reserva {

	private String id;
	private Timestamp hora_inicio, hora_fin;
	private String id_usuario, codigo_sala;
	private Timestamp hora_entrada, hora_salida;

	public Reserva(String id, Timestamp hora_inicio, Timestamp hora_fin, String id_usuario, String codigo_sala) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
		this.id_usuario = id_usuario;
		this.codigo_sala = codigo_sala;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getCodigo_sala() {
		return codigo_sala;
	}

	public void setCodigo_sala(String codigo_sala) {
		this.codigo_sala = codigo_sala;
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
