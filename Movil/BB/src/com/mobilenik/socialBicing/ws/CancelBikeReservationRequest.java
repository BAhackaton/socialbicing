package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;

public class CancelBikeReservationRequest extends MKRequest implements IMKRequest
{   
	
	public int idUser;
	private int idBike;
	public String reserveCode;
	public String cancelationReason;
    
    public CancelBikeReservationRequest(int idUser, int idBike, String reserveCode, String cancelationReason)
    {
        this.idUser = idUser;
        this.idBike = idBike;
        this.reserveCode = reserveCode;
        this.cancelationReason = cancelationReason;
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
		return "CancelBikeReservation";
	}
}
