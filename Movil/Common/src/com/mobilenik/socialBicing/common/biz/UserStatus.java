package com.mobilenik.socialBicing.common.biz;

import java.util.Vector;

public class UserStatus {

	private int idStatus;
	private Vector bikesAssigned;
	private Reserva reserve;


	public UserStatus(int idStatus, Vector bikesAssigned, Reserva reserve){
		this.idStatus = idStatus;
		this.bikesAssigned = bikesAssigned;
		this.reserve = reserve;
	}


	public int getIdStatus() {
		return idStatus;
	}


	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}


	public Vector getBikesAssigned() {
		return bikesAssigned;
	}


	public void setBikesAssigned(Vector bikesAssigned) {
		this.bikesAssigned = bikesAssigned;
	}


	public Reserva getReserve() {
		return reserve;
	}


	public void setReserve(Reserva reserve) {
		this.reserve = reserve;
	}

	
}
