package com.mobilenik.socialBicing.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mobilenik.core.xml.ParseBodyException;
import com.mobilenik.core.xml.ParseHeaderException;
import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.TiposUsuario;
import com.mobilenik.socialBicing.common.biz.UserStatus;
import com.mobilenik.socialBicing.common.biz.UsuarioFinal;
import com.mobilenik.socialBicing.common.biz.UsuarioPuesto;
import com.mobilenik.util.utiles.XmlUtils;

/**
 * 
 */
public class LoginResult extends MKResult 
{
	public AbsUsuario usuario;
	public UserStatus userStatus;

	public void parseHeader(Document doc) throws ParseHeaderException {
		header = new MKResultHeader("LoginResult");
		header.parse(doc);
	}

	protected void parseBody(Document doc) throws ParseBodyException
	{
		try
		{
			if(this.header.codigo == 0){
				Element root = (Element)doc.getElementsByTagName("LoginResult").item(0);

				int id = Integer.parseInt( XmlUtils.getNodeValue(root, "id"));
				String name = XmlUtils.getNodeValue(root, "name");
				String strProfile = XmlUtils.getNodeValue(root, "profile");
				int tipoUsuario = Integer.parseInt(strProfile);
				Element eUserStatus = XmlUtils.getNode(root, "userStatus");
				UserStatus userStatus = ResultHelper.parseUserStatus(eUserStatus);
				
				this.userStatus = userStatus;
				
				if(tipoUsuario==TiposUsuario.USUARIO_FINAL){
					//            	Bici biciAsignada = new Bici(1, "", new LatLng(0, 0), "", new UsuarioFinal("", "", null));
					Bici biciAsignada = null;
					if(userStatus.getBikesAssigned().size()>0){
						biciAsignada = (Bici)userStatus.getBikesAssigned().elementAt(0);
					}
					usuario = new UsuarioFinal(id, name, userStatus.getIdStatus(), biciAsignada);
				}
				else if(tipoUsuario==TiposUsuario.USUARIO_PUESTO){
					usuario = new UsuarioPuesto(id, name, userStatus.getIdStatus(), userStatus.getBikesAssigned());
				}
			}
		}
		catch(Exception e)
		{
			throw new ParseBodyException("LoginResultE - ParseBodyException: "+e);
		}
	}//parseBody 
} 
