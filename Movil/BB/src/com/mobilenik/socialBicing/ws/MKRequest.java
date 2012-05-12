package com.mobilenik.socialBicing.ws;


/**
 * 
 */
public abstract class MKRequest
{
    protected static final String nameSpace = "xmlns=\"http://tempuri.org/\""; 
    
    public String toXML()
    {
        String xmlDocumentString =         
        	"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +	
        	"<soap:Body>"+
	    		    "<" + getNodeServiceName() + " xmlns=\"http://tempuri.org/\">" +
			                getBodyXML() +
	                "</" + getNodeServiceName() + ">"+
	             "</soap:Body>"+
			"</soap:Envelope>";

        return xmlDocumentString;
    }
    
    
    protected abstract String getNodeServiceName();
    protected abstract String getBodyXML();
} 
