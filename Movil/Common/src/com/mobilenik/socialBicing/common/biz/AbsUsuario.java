package com.mobilenik.socialBicing.common.biz;


public abstract class AbsUsuario {

	
	private String nombre;
	private int estadoUsuario;
	private int id;

	public AbsUsuario(int id, String nombre, int estadoUsuario){
		this.setId(id);
		this.setNombre(nombre);
		this.setEstadoUsuario(estadoUsuario);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setEstadoUsuario(int estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public int getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
