package com.mobilenik.socialBicing.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mobilenik.core.xml.ParseHeaderException;
import com.mobilenik.util.utiles.XmlUtils;

/**
 * Header salida
 * WS >> BB
 */
public class MKResultHeader
{
	public int codigo;
	public String descripcion;
	protected String rootName;
	
    public MKResultHeader(String rootName) {
		this.rootName = rootName;
	}

	public void parse(Document doc) throws ParseHeaderException
    {
    	try
        {
    		Element root = (Element)doc.getElementsByTagName(rootName).item(0);
        	codigo = Integer.parseInt(XmlUtils.getNodeValue(root, "resCod"));
        	descripcion = XmlUtils.getNodeValue(root, "resDesc");
        }
        catch(Exception e)
        {
            throw new ParseHeaderException("ParseHeaderException");
        }
    }
}
