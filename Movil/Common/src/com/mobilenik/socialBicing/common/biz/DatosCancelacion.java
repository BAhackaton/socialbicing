package com.mobilenik.socialBicing.common.biz;

public class DatosCancelacion {

	private String mensaje;
	
	public DatosCancelacion(String mensaje){
		this.setMensaje(mensaje);
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}
