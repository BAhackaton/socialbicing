package com.mobilenik.socialBicing.common.biz;

public class EstadoBici {

	public static final int STATE_FREE = 2;
	public static final int STATE_RESERVED = 3;
	public static final int STATE_IN_USE = 4;
	public static final int STATE_IN_STATION = 5;
	public static final int STATE_OUT_OF_SERVICE = 6;

	public static String toString(int idState){
		String value = "-";

		switch (idState) {
		case STATE_FREE:
			value = "Libre";
			break;
		case STATE_RESERVED:
			value = "Reservada";
			break;
		case STATE_IN_USE:
			value = "En uso";
			break;
		case STATE_IN_STATION:
			value = "En la estación";
			break;
		case STATE_OUT_OF_SERVICE:
			value = "Fuera de servicio";
			break;
		default:
			break;
		}
		

		return value;
	}
}
