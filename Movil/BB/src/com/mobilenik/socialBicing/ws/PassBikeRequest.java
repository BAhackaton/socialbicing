package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;

public class PassBikeRequest extends MKRequest implements IMKRequest
{   
	
	public int idUser;
	private int idBike;
	private String reserveCode;

    
    public PassBikeRequest(int idUser, int idBike, String reserveCode)
    {
        this.idUser = idUser;
        this.idBike = idBike;
        this.reserveCode = reserveCode;
    }


    public String getBodyXML()
    {
        String bodyXmlStr =
    		"<idUser>" + this.idUser + "</idUser>" +
    		"<idBike>" + this.idBike + "</idBike>" +
    		"<reserveCode>" + this.reserveCode + "</reserveCode>";
        return bodyXmlStr;
    }

	protected String getNodeServiceName() {
		return "PassBike";
	}
}
