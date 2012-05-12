package com.mobilenik.socialBicing.common.biz;

public class Reserva {

	private long expirationTime;
	private int idBike;
	private Bici bike;
	private String reserveCode;

	public Reserva(int idBike, long expirationTime, String reserveCode, Bici bike){
		this.setIdBike(idBike);
		this.setExpirationTime(expirationTime);
		this.setReserveCode(reserveCode);
		this.setBike(bike);
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public String getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(String reserveCode) {
		this.reserveCode = reserveCode;
	}


	public int getIdBike() {
		return idBike;
	}


	public void setIdBike(int idBike) {
		this.idBike = idBike;
	}

	public Bici getBike() {
		return bike;
	}

	public void setBike(Bici bike) {
		this.bike = bike;
	}
}