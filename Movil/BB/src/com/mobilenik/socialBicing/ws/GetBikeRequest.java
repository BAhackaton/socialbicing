package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;

public class GetBikeRequest extends MKRequest implements IMKRequest
{   
	
	private int idUser;
	private int idBike;

    
    public GetBikeRequest(int idUser, int idBike)
    {
        this.idUser = idUser;
        this.idBike = idBike;
    }


    public String getBodyXML()
    {
        String bodyXmlStr =
    		"<idUser>" + this.idUser + "</idUser>" +
    		"<idBike>" + this.idBike + "</pwd>";
        return bodyXmlStr;
    }

	protected String getNodeServiceName() {
		return "GetBike";
	}
}
