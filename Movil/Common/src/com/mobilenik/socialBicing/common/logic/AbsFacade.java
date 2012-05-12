package com.mobilenik.socialBicing.common.logic;

import java.util.Vector;

import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.DatosCancelacionReserva;
import com.mobilenik.socialBicing.common.biz.DatosCancelacionRetiro;
import com.mobilenik.socialBicing.common.biz.DatosRetiroBici;
import com.mobilenik.socialBicing.common.biz.LatLng;


public abstract class AbsFacade{

	
	protected AbsFacade(){
	}
	
	
	////////////////////////////////////
	//		ACCIONES DEL USUARIO 	  //
	////////////////////////////////////
	
	public abstract AbsUsuario login(String nombreUsuario, String password);
	
	public abstract boolean reservar(AbsUsuario usuario, Bici bici);
	
	public abstract DatosRetiroBici retirar(AbsUsuario usuario, Bici bici);
	
	public abstract boolean liberarBici(AbsUsuario usuario, Bici bici);
	
	public abstract boolean cancelarReserva(AbsUsuario usuario, Bici bici, DatosCancelacionReserva datos);

	public abstract boolean cancelarRetiro(AbsUsuario usuario, Bici bici, DatosCancelacionRetiro datos);

	////////////////////////////////////
	//		LLAMADAS AL BACKEND 	  //
	////////////////////////////////////
	
	
	/**
	 * Devuelve las bicis libre con el centro y amplitud en kms tomados como parámetro
	 * @param latLng
	 * @param kms
	 * @return
	 */
	public abstract Vector getBicisLibres(LatLng latLng, int kms);

	
	////////////////////////////////////////
	//		LLAMADAS A OTROS SERVICIOS	  //
	////////////////////////////////////////
	
	public abstract LatLng getLatLng(String calle);
	
}
