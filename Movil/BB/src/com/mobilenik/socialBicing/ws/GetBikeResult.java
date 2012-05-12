package com.mobilenik.socialBicing.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mobilenik.core.xml.ParseBodyException;
import com.mobilenik.core.xml.ParseHeaderException;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.util.utiles.XmlUtils;

public class GetBikeResult extends MKResult 
{
	public Bici bike;

	public void parseHeader(Document doc) throws ParseHeaderException {
		header = new MKResultHeader("GetBikeResult");
		header.parse(doc);
	}

	protected void parseBody(Document doc) throws ParseBodyException
	{
		try
		{
			if(this.header.codigo == 0){

				Element root = (Element)doc.getElementsByTagName(this.header.rootName).item(0);

				Element eBike = XmlUtils.getNode(root, "bike");

				this.bike = ResultHelper.parseBike(eBike);
			}
		}
		catch(Exception e)
		{
			throw new ParseBodyException("GetBikeResult - ParseBodyException: "+e);
		}
	}
}