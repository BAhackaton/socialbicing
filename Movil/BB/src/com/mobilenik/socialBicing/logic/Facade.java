//package com.mobilenik.socialBicing.logic;
//
//import java.util.Vector;
//
//import com.mobilenik.core.logic.events.MKEvent;
//import com.mobilenik.socialBicing.common.biz.AbsUsuario;
//import com.mobilenik.socialBicing.common.biz.Bici;
//import com.mobilenik.socialBicing.common.biz.DatosCancelacionReserva;
//import com.mobilenik.socialBicing.common.biz.DatosCancelacionRetiro;
//import com.mobilenik.socialBicing.common.biz.DatosRetiroBici;
//import com.mobilenik.socialBicing.common.biz.LatLng;
//import com.mobilenik.socialBicing.common.logic.AbsFacade;
//import com.mobilenik.socialBicing.ws.LoginRequest;
//
//
//public class Facade extends AbsFacade{
//
//	private static Facade instance;
//	
//	private Facade(){
//		super();
//	}
//	
//	public static AbsFacade getInstace(){
//		if(instance==null){
//			instance = new Facade();
//		}
//		return instance;
//	}
//	
//	////////////////////////////////////
//	//		ACCIONES DEL USUARIO 	  //
//	////////////////////////////////////
//	
//	public AbsUsuario login(String nombreUsuario, String password){
//		MKApplicationControllerE.getInstance().mkEventHandler(new MKEvent(MKEventTypesE.LOGIN_REQUEST, 
//				new LoginRequest(nombreUsuario, password)));
//		return null;
//	}
//	
//	public boolean reservar(AbsUsuario usuario, Bici bici){
//		return false;
//	}
//	
//	public DatosRetiroBici retirar(AbsUsuario usuario, Bici bici){
//		return null;
//	}
//	
//	public boolean liberarBici(AbsUsuario usuario, Bici bici){
//		return false;
//	}
//	
//	public boolean cancelarReserva(AbsUsuario usuario, Bici bici, DatosCancelacionReserva datos){
//		return false;
//	}
//
//	public boolean cancelarRetiro(AbsUsuario usuario, Bici bici, DatosCancelacionRetiro datos)
//	{
//		return false;
//	}
//
//	////////////////////////////////////
//	//		LLAMADAS AL BACKEND 	  //
//	////////////////////////////////////
//	
//	
//	/**
//	 * Devuelve las bicis libre con el centro y amplitud en kms tomados como parámetro
//	 * @param latLng
//	 * @param kms
//	 * @return
//	 */
//	public Vector getBicisLibres(LatLng latLng, int kms){
//		Vector ret = new Vector();
//		return ret;
//	}
//	
//
//	
//	////////////////////////////////////////
//	//		LLAMADAS A OTROS SERVICIOS	  //
//	////////////////////////////////////////
//	
//	public LatLng getLatLng(String calle){
//		return null;
//	}
//}
