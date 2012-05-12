package com.mobilenik.socialBicing.common.biz;

public class Bici {

	private int estado;
	private int id;
	private String code;
	private LatLng latLng;
	private String calle;
	private Reserva reserva;
	private int responsable;


	public Bici(int estado, int id, LatLng latLng, String calle, int responsable, String code){
		this.estado = estado;
		this.id = id;
		this.latLng = latLng;
		this.calle  = calle;
		this.responsable = responsable;
		this.code = code;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getEstado() {
		return estado;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCalle() {
		return calle;
	}

	public void setResponsable(/*AbsUsuario*/int responsable) {
		this.responsable = responsable;
	}

	public /*AbsUsuario*/int getResponsable() {
		return responsable;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
