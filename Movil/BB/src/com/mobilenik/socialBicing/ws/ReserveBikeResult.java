package com.mobilenik.socialBicing.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mobilenik.core.xml.ParseBodyException;
import com.mobilenik.core.xml.ParseHeaderException;
import com.mobilenik.socialBicing.common.biz.UserStatus;
import com.mobilenik.util.utiles.XmlUtils;

public class ReserveBikeResult extends MKResult 
{
	public String reserveCode;
	public UserStatus userStatus;

	public void parseHeader(Document doc) throws ParseHeaderException {
		this.header = new MKResultHeader("ReserveBikeResult");
		this.header.parse(doc);
	}

	protected void parseBody(Document doc) throws ParseBodyException
	{
		try
		{
			if(this.header.codigo == 0){
				Element root = (Element)doc.getElementsByTagName(this.header.rootName).item(0);

				this.reserveCode = XmlUtils.getNodeValue(root, "reserveCode");

				Element eUserStatus = XmlUtils.getNode(root, "userStatus");
				this.userStatus = ResultHelper.parseUserStatus(eUserStatus);
			}
		}
		catch(Exception e)
		{
			throw new ParseBodyException("ReserveBikeResult - ParseBodyException: "+e);
		}
	}
}