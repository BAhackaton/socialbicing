package com.mobilenik.socialBicing.common.biz;

public class LatLng {

	private double lat;
	private double lng;
	
	public LatLng(double lat, double lng){
		this.setLat(lat);
		this.setLng(lng);
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLng() {
		return lng;
	}
}
