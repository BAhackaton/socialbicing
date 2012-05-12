package com.mobilenik.socialBicing.common.biz;

public class Place {

	private String address;
	private LatLng point;
	
	public Place(String address, LatLng point){
		this.setAddress(address);
		this.setPoint(point);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LatLng getPoint() {
		return point;
	}

	public void setPoint(LatLng point) {
		this.point = point;
	}

}
