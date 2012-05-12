package com.mobilenik.socialBicing.ws;


import com.mobilenik.core.ws.IMKRequest;

public class LoginRequest extends MKRequest implements IMKRequest
{   
	
	private String user;
	private String password;

    
    public LoginRequest(String user, String password)
    {
        this.user = user;
        this.password = password;
    }

    public String getUser(){
    	return user;
    }
    
    public String getPassword(){
    	return password;
    }

    public String getBodyXML()
    {
        String bodyXmlStr =
    		"<usr>" + user + "</usr>" +
    		"<pwd>" + password + "</pwd>";
        return bodyXmlStr;
    }

	protected String getNodeServiceName() {
		return "Login";
	}
}
