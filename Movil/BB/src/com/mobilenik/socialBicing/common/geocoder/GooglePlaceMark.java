package com.mobilenik.socialBicing.common.geocoder;

import java.util.Vector;

import com.mobilenik.util.common.Functions;


/**
 * Representa un lugar, con los datos usados por la Api de Google Maps
 */
public class GooglePlaceMark {
	
	private String name;
	private int accuracy;
	private double latitude;
	private double longitude;
	private String coordinates;
	private boolean favorite;
	
	public GooglePlaceMark(String name, double latitude, double longitude, boolean favorite){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.favorite = favorite;
	}
	
	public GooglePlaceMark() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	
	/**
	 * Setea las coordenadas, Longitud y Latitud
	 * @param coordinates Las Coordenadas como las devuelve la api de google "longitud,latitud"
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
		//Separo y guardo las coordenadas
		Vector latAndLong = Functions.split(",", coordinates);
		this.longitude = Double.parseDouble((String)latAndLong.elementAt(0));
		this.latitude = Double.parseDouble((String)latAndLong.elementAt(1));
	}

	public boolean equals(Object obj) {
		if(obj != null){
			GooglePlaceMark place = (GooglePlaceMark)obj;
			if(this.latitude == place.latitude && this.longitude == place.longitude){
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "GooglePlaceMark [name=" + name + ", coordinates=" + coordinates
				+ ", favorite=" + favorite + "]";
	}
	
}
