package com.mobilenik.socialBicing.logic;

import com.mobilenik.core.logic.MKConfigurationManager;

public class MKConfigurationManagerE extends MKConfigurationManager{

	private static String HOST_URL_BASE;

	static{
		switch (CORRER_EN_PRODUCCION){
		case 0: 
			HOST_URL_BASE = "";

			
			break;
		case 1:
			//desarrollo
			HOST_URL_BASE = "http://ba.mobilenik.com:81/SocialBicing/SocialBicing.asmx";
//			HOST_URL_BASE = "http://desasvm2.mobilenik.com.ar/SocialBicing/SocialBicing.asmx";
			break;
//		case 2: 
//			 //homologación
//			HOST_URL_BASE = "";
//			break;
		}
	}

	public static String getWSServerE()
	{
		return HOST_URL_BASE;
	}
	
}