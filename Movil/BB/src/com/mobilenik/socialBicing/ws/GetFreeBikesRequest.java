package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;

public class GetFreeBikesRequest extends MKRequest implements IMKRequest
{   
	
	private int idUser;
	private double latitud;
	private double longitud;

    
    public GetFreeBikesRequest(int idUser, double latitud, double longitud)
    {
        this.idUser = idUser;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    public String getBodyXML()
    {
        String bodyXmlStr =
    		"<idUser>" + this.idUser + "</idUser>" +
    		"<latitude>" + this.latitud + "</latitude>" +
    		"<longitude>" + this.longitud + "</longitude>";
        return bodyXmlStr;
    }

	protected String getNodeServiceName() {
		return "GetFreeBikes";
	}
}
