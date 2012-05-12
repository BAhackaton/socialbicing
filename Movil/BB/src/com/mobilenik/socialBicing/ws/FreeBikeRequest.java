package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;
import com.mobilenik.socialBicing.common.biz.Place;

public class FreeBikeRequest extends MKRequest implements IMKRequest
{   
	
	public int idUser;
	private int idBike;
	private Place place;

    
    public FreeBikeRequest(Place place, int idUser, int idBike)
    {
        this.idUser = idUser;
        this.idBike = idBike;
        this.place = place;
    }


    public String getBodyXML()
    {
        String bodyXmlStr =
    		"<idUser>" + this.idUser + "</idUser>" +
    		"<idBike>" + this.idBike + "</idBike>" +
    		"<place>" +
    			"<address>" + this.place.getAddress() + "</address>" +
    			"<latitude>" + this.place.getPoint().getLat() + "</latitude>" +
    			"<longitude>" + this.place.getPoint().getLng() + "</longitude>" +
    		"</place>";
        return bodyXmlStr;
    }

	protected String getNodeServiceName() {
		return "FreeBike";
	}
}
