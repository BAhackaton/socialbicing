package com.mobilenik.socialBicing.ws;

import java.util.Date;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mobilenik.core.MKCalendar;
import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.LatLng;
import com.mobilenik.socialBicing.common.biz.Reserva;
import com.mobilenik.socialBicing.common.biz.UserStatus;
import com.mobilenik.socialBicing.common.biz.UsuarioFinal;
import com.mobilenik.util.utiles.XmlUtils;

public class ResultHelper {

	public static Bici parseBike(Element rootNode){
		int bikeId = Integer.parseInt(XmlUtils.getNodeValue(rootNode, "id"));
		String bikeCode = XmlUtils.getNodeValue(rootNode, "code");
		String strBikeIdState = XmlUtils.getNodeValue(rootNode, "idState");
		int bikeIdState = Integer.parseInt(strBikeIdState);
		String strBikeLatitude = XmlUtils.getNodeValue(rootNode, "latitude");
		double bikeLatitude = Double.parseDouble(strBikeLatitude);
		String strBikeLongitude = XmlUtils.getNodeValue(rootNode, "longitude");
		double bikeLongitude = Double.parseDouble(strBikeLongitude);
		LatLng bikeLatLng = new LatLng(bikeLatitude, bikeLongitude);
		String bikeAddress = XmlUtils.getNodeValue(rootNode, "address");
		String strBikeIdUser = XmlUtils.getNodeValue(rootNode, "idUser");
		int idUser = Integer.parseInt(strBikeIdUser);

		return new Bici(bikeIdState, bikeId, bikeLatLng, bikeAddress, idUser, bikeCode);
	}

	public static AbsUsuario parseUser(Element rootNode){
		int id = Integer.parseInt( XmlUtils.getNodeValue(rootNode, "id"));
		String name = XmlUtils.getNodeValue(rootNode, "name");
		AbsUsuario user = new UsuarioFinal(id, name, 0, null);
		return user;
	}

	public static UserStatus parseUserStatus(Element rootNode){
		UserStatus model;

		String strIdStatus = XmlUtils.getNodeValue(rootNode, "idStatus");
		int idStatus = Integer.parseInt(strIdStatus);

		Element eBikesAssigned = XmlUtils.getNode(rootNode, "bikesAssigned");
		NodeList listBikes = XmlUtils.getNodeList(eBikesAssigned, "Bike");
		Vector bicisAsignadas = new Vector();
		for (int i=0; i<listBikes.getLength(); i++) {
			Element eBike = (Element)listBikes.item(i);
			Bici bike = ResultHelper.parseBike(eBike);
			bicisAsignadas.addElement(bike);
		}

		Reserva reserve = null;
		Element eReserve = XmlUtils.getNode(rootNode, "reserve");
		reserve = ResultHelper.parseReserve(eReserve);
		model = new UserStatus(idStatus, bicisAsignadas, reserve);
		return model;
	}

	public static Reserva parseReserve(Element rootNode){
		Reserva reserve = null;
		if(rootNode != null){
			String expirationTimeValue = XmlUtils.getNodeValue(rootNode, "expirationTime");
			long expirationTime = dateToLong(expirationTimeValue);
			String reserveCode = XmlUtils.getNodeValue(rootNode, "randomCode");

			int idBike = Integer.parseInt(XmlUtils.getNodeValue(rootNode, "idBike"));
			Element eBike = XmlUtils.getNode(rootNode, "Bike");
			Bici bike = ResultHelper.parseBike(eBike);

			reserve = new Reserva(idBike, expirationTime, reserveCode, bike);
		}
		return reserve;
	}

	public static long dateToLong(String dateValue){
		try {
			Date date = MKCalendar.getInstance().getDate(dateValue, MKCalendar.SOAP_DATE);
			return date.getTime();
		} catch (Exception e) {
			return System.currentTimeMillis();
		}		
	}
}
