package com.mobilenik.socialBicing.logic;

import com.mobilenik.core.logic.events.MKEventTypes;

/**
 * GO  eventos de navegacion, generalmente no envian parámetros
 * DO  eventos de accion, pueden o no llevar parametros
 * REQUEST eventos generados en las pantallas o en el appController, llevan parametros
 * RESULT  eventos generados en el connectionManager, llevan parametros
 */
public class MKEventTypesE extends MKEventTypes
{   
    public static final int DO_START_APP = 1;
    public static final int LOGIN_REQUEST = 101;
    public static final int LOGIN_RESULT = 102;
    public static final int GO_SPLASH_SCREEN = 200;
    public static final int GO_LOGIN_SCREEN = 201;
	public static final int GO_BICI_SCREEN = 202;
	public static final int GO_SEARCH_BIKESCREEN = 203;
	public static final int GET_BIKE_REQUEST = 103;
	public static final int GET_BIKE_RESULT = 104;
	public static final int GET_FREE_BIKES_REQUEST = 105;
	public static final int GET_FREE_BIKES_RESULT = 106;
	
	
	public static final int RESERVE_BIKE_REQUEST = 107;
	public static final int RESERVE_BIKE_RESULT = 108;
	public static final int USE_BIKE_REQUEST = 109;
	public static final int USE_BIKE_RESULT = 110;
	public static final int FREE_BIKE_REQUEST = 111;
	public static final int FREE_BIKE_RESULT = 112;
	public static final int CANCEL_BIKE_RESERVATION_REQUEST = 113;
	public static final int CANCEL_BIKE_RESERVATION_RESULT = 114;
	public static final int PASS_BIKE_REQUEST = 115;
	public static final int PASS_BIKE_RESULT = 116;
	public static final int GO_DROP_BIKE_SCREEN = 117;
	public static final int GO_FREE_BIKE_SCREEN = 118;
	public static final int GO_PASS_BIKE_SCREEN = 119;
	public static final int GO_BACK = 120;
	public static final int GO_CANCEL_RESERVATION_SCREEN = 121;
}
