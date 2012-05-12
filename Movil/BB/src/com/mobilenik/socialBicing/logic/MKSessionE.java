package com.mobilenik.socialBicing.logic;

import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.UserStatus;


/**
 * Singleton
 * Datos de sesion utilizados por MKApplicationController
 */
public class MKSessionE 
{


	private static MKSessionE instance = new MKSessionE();
	public AbsUsuario usuario;
	public UserStatus userStatus;
	public String reserveCode;
	
	private MKSessionE() {}
	
	public static MKSessionE getInstance()
	{ 
		return instance; 
	}

}
