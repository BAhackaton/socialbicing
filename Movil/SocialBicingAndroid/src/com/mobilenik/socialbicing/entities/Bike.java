package com.mobilenik.socialbicing.entities;

public class Bike {

	private String code;

	private int latitude;

	private int longitude;

	public Bike(int lat, int lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	public String getCode() {
		return code;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

}
