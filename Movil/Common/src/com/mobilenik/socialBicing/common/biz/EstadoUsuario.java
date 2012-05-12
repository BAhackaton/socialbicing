package com.mobilenik.socialBicing.common.biz;

public class EstadoUsuario {

	public static final int STATE_NO_BIKE = 7;
	public static final int STATE_BIKE_ASSIGNED = 8;
	public static final int STATE_BIKE_RESERVED = 9;
	
	public static String toString(int idState){
		String value = "-";

		switch (idState) {
		case STATE_NO_BIKE:
			value = "Sin bicicleta asignada";
			break;
		case STATE_BIKE_RESERVED:
			value = "Bicicleta reservada";
			break;
		case STATE_BIKE_ASSIGNED:
			value = "Bicicleta asingnada";
			break;
		default:
			break;
		}
		

		return value;
	}
}
