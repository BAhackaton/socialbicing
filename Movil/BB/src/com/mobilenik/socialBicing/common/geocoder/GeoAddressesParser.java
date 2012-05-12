package com.mobilenik.socialBicing.common.geocoder;

import java.io.IOException;
import java.util.Vector;

import com.mobilenik.util.ws.KXml.KXmlParser;
import com.mobilenik.util.ws.KXml.XmlPullParser;
import com.mobilenik.util.ws.KXml.XmlPullParserException;

/**
 * Parser del XML que devuelve Google ante una busqueda por dirección.
 * Obtiene los Placemarks con sus datos.
 */
public class GeoAddressesParser {

	
	/**
	 * Parsea el XML completo de la respuesta de Google y devuelve los PlaceMarks
	 * @param parser El parser con el InputStreamReader del XML seteado
	 * @return Un Vector de los GooglePlacemarks encontrados.. 
	 */
	public Vector parseBody(KXmlParser parser){
		Vector direcciones = new Vector();
		try {
			int elementType;
			while((elementType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				if(elementType == XmlPullParser.START_TAG){
					if(parser.getName().equals("Placemark")){
						direcciones.addElement(parsePlaceMark(parser));
					}			
				}
			}
		} catch (Exception e) {
			
		}
		return direcciones; 
	}
	
	/**
	 * Parsea cada segmento placemark y devuelve un elemento GooglePlacemark completo
	 * @param parser El parser actual en posicion de inicio del PlaceMark
	 * @return El GooglePlaceMark con todos sus datos
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private GooglePlaceMark parsePlaceMark(KXmlParser parser) throws XmlPullParserException, IOException{
		GooglePlaceMark place = new GooglePlaceMark();
		int elementType;
		while((elementType = parser.next()) != XmlPullParser.END_DOCUMENT){
			if(elementType == XmlPullParser.START_TAG){
				//Lleno cada campo necesario
				if(parser.getName().equals("address")){
					place.setName(parser.nextText());					
				}else if(parser.getName().equals("AddressDetails")){
					place.setAccuracy(Integer.parseInt(parser.getAttributeValue(parser.getNamespace(), "Accuracy")));					
				}else if(parser.getName().equals("coordinates")){
					place.setCoordinates(parser.nextText());
					return place;
				}
			}
		}
		return place;
	}

	
}
