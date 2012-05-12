package com.mobilenik.socialBicing.common;

import javax.microedition.location.Coordinates;

public class Functions {

	public static double getDistances(double lat1, double lng1, double lat2, double lng2)
	{
		Coordinates c1 = new Coordinates(lat1,lng1,0);
		Coordinates c2 = new Coordinates(lat2,lng2,0);
		return  c1.distance(c2) / 1000; 
	}

	public static String timeStamToString(long date1, long date2){
		long delta = (date1 - date2);

//		long seconds = (delta / 1000) % 60;
		long minutes = ((delta / (1000 * 60)) % 60);
		long hours = ((delta / (1000 * 60 * 60)) % 24);

		String time;

		if (hours > 0) {
			time = hours > 1 ? hours + " horas" : "1 hora";
		} else if (minutes > 0) {
			time = minutes > 1 ? minutes + " minutos" : "1 minuto";
		} else {
			time = "menos de 1 minuto";
		}

		return time;
		//		return "5 Mins.";
	}

	public static String distanceToString(double distanceKm) {
		if (distanceKm > 0) {
			if (distanceKm < 1){
				return Integer.toString((int)(distanceKm * 1000)) + " mts";
			}
			else{
				return Functions.format(distanceKm, 2) + " km";
			}
		} else{
			return "";
		}
	}

	public static String format( double value, int digits ) {
		String ret;
		Double od = new Double(round( value, digits ));
		String str = od.toString();
		// si es -0.0 quita el "-" de adelante
		if (value == 0 && str.startsWith("-")) {
			str = str.substring(1);
		}
		// busca el punto.
		int pos = str.indexOf(".");
		String intPart = str.substring(0, pos);
		if (digits <= 0) {
			ret = addMilesSeparator(intPart, '.');
		}
		else {
			String decPart = str.substring(pos + 1, str.length());
			if (decPart.length() > digits) {
				decPart = decPart.substring( 0, digits );
			}
			else {
				while (decPart.length() < digits) {
					decPart += "0";
				}
			}                

			ret = addMilesSeparator(intPart, '.') + "," + decPart;
		}
		return ret;
	}

	public static String addMilesSeparator(String value, char digit){
		StringBuffer sb;
		int posi;

		sb = new StringBuffer();        
		posi = 1;

		for(int i = value.length()-1; i >= 0 ;i--){
			sb.insert(0, value.charAt(i));
			if(posi % 3 == 0 && i > 0 && value.charAt(i-1) != '-'){
				sb.insert(0, digit);
			}
			posi++;
		}

		return sb.toString();
	}

	public static double round(double d, int decimals) {

		boolean neg = d < 0;
		if (neg) {
			d = -d;
		}

		// Calcula shift = 10^decimals
		double shift = 1;
		for (int i = 0; i < decimals; i++) {
			shift *= 10;
		}

		// amplifico el dato.
		double amp  = d * shift;
		// trunca el dato amplificado
		double trunc = Math.floor( amp );
		// obtiene con la resta la mantisa que queda.
		double rest = amp - trunc;

		if (rest >= 0.5) {
			trunc += 1;
		}

		double ret = trunc / shift;

		if (neg) {
			ret = -ret;
		}

		return ret;

	}   
}
