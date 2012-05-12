package com.mobilenik.socialBicing.ws;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mobilenik.core.xml.ParseBodyException;
import com.mobilenik.core.xml.ParseHeaderException;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.UserStatus;
import com.mobilenik.util.utiles.XmlUtils;

/**
 * 
 */
public class GetFreeBikesResult extends MKResult 
{
	public UserStatus userStatus;
	public Vector bikes;

	public void parseHeader(Document doc) throws ParseHeaderException {
		this.header = new MKResultHeader("GetFreeBikesResult");
		this.header.parse(doc);
	}

	protected void parseBody(Document doc) throws ParseBodyException
	{
		try
		{
			if(this.header.codigo == 0){
				Element root = (Element)doc.getElementsByTagName(this.header.rootName).item(0);

				Element eUserStatus = XmlUtils.getNode(root, "userStatus");
				userStatus = ResultHelper.parseUserStatus(eUserStatus);
				
				bikes = new Vector();
				Element eBikes = XmlUtils.getNode(root, "bikes");
				NodeList listBikes = XmlUtils.getNodeList(eBikes, "Bike");
				for (int i=0; i<listBikes.getLength(); i++) {
					Element eBike = (Element)listBikes.item(i);
					Bici bike = ResultHelper.parseBike(eBike);
					bikes.addElement(bike);
				}
			}
		}
		catch(Exception e)
		{
			throw new ParseBodyException("GetFreeBikesResult - ParseBodyException: "+e);
		}
	}
}