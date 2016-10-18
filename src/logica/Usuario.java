package logica;

import java.sql.Timestamp;

public class Usuario {
	private int id_usuario;
	private String dni,nombre,apellidos,direccion,email,ciudad,password;
	private double cuota;
	private boolean baja;
	private Timestamp fecha_baja;
	
	public Usuario(int id_usuario){
		this.id_usuario = id_usuario;
	}
	
	public Usuario(int id_usuario, String dni, String nombre, String apellidos, String direccion, String email,
			String ciudad, String password, double cuota, boolean baja, Timestamp fecha_baja) {
		super();
		this.id_usuario = id_usuario;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.email = email;
		this.ciudad = ciudad;
		this.password = password;
		this.cuota = cuota;
		this.baja = baja;
		this.fecha_baja = fecha_baja;
	}
	
	public Usuario(String nombre){
		this.nombre = nombre;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getCuota() {
		return cuota;
	}

	public void setCuota(double cuota) {
		this.cuota = cuota;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public Timestamp getFecha_baja() {
		return fecha_baja;
	}

	public void setFecha_baja(Timestamp fecha_baja) {
		this.fecha_baja = fecha_baja;
	}	
}