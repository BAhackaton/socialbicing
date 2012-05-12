package com.mobilenik.socialBicing.logic;

import java.util.Vector;

import net.rim.device.api.ui.component.Dialog;

import com.mobilenik.core.logic.events.IMKEventListener;
import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.logic.events.MKEventTypes;
import com.mobilenik.core.ui.screens.Loading;
import com.mobilenik.core.ui.screens.MKScreen;
import com.mobilenik.core.ui.screens.MKScreenTypes;
import com.mobilenik.core.ws.IMKRequest;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.ui.screens.BikeScreen;
import com.mobilenik.socialBicing.ui.screens.BikeSearchScreen;
import com.mobilenik.socialBicing.ui.screens.CancelReservationBikeScreen;
import com.mobilenik.socialBicing.ui.screens.FreeBikeScreen;
import com.mobilenik.socialBicing.ui.screens.PassBikeScreen;
import com.mobilenik.socialBicing.ui.screens.ReserveBikeScreen;
import com.mobilenik.socialBicing.ui.screens.ScreenFactory;
import com.mobilenik.socialBicing.ui.screens.SplashScreen;
import com.mobilenik.socialBicing.ws.CancelBikeReservationRequest;
import com.mobilenik.socialBicing.ws.CancelBikeReservationResult;
import com.mobilenik.socialBicing.ws.FreeBikeRequest;
import com.mobilenik.socialBicing.ws.FreeBikeResult;
import com.mobilenik.socialBicing.ws.GetBikeRequest;
import com.mobilenik.socialBicing.ws.GetBikeResult;
import com.mobilenik.socialBicing.ws.GetFreeBikesRequest;
import com.mobilenik.socialBicing.ws.GetFreeBikesResult;
import com.mobilenik.socialBicing.ws.LoginRequest;
import com.mobilenik.socialBicing.ws.LoginResult;
import com.mobilenik.socialBicing.ws.PassBikeRequest;
import com.mobilenik.socialBicing.ws.PassBikeResult;
import com.mobilenik.socialBicing.ws.ReserveBikeRequest;
import com.mobilenik.socialBicing.ws.ReserveBikeResult;
import com.mobilenik.socialBicing.ws.UseBikeRequest;
import com.mobilenik.socialBicing.ws.UseBikeResult;
import com.mobilenik.util.common.Functions;
import com.mobilenik.util.device.Logger;
import com.mobilenik.util.device.MKDevice;
import com.mobilenik.util.location.GPSLocationProvider;
import com.mobilenik.util.location.GpsConfiguration;
import com.mobilenik.util.location.LocationManager;
import com.mobilenik.util.location.Position;
import com.mobilenik.util.location.RadioConfiguration;
import com.mobilenik.util.location.RadioLocationProvider;
import com.mobilenik.util.location.exceptions.StartException;
import com.mobilenik.util.location.geocoder.GoogleGeocoder;

/**
 * Singleton
 * Controla pantallas y acceso a WebServices
 */
public class MKApplicationControllerE implements IMKEventListener
{

	private static MKApplicationControllerE instance = new MKApplicationControllerE();


	private MKMainAppE app;
	private MKConnectionManagerE connMgr;
	private MKSessionE session;
	private ScreenFactory screenFactory;
	private MKScreen currentScreen;
	private MKScreen loadingScreen;
	private LocationManager locationManager;

	private MKApplicationControllerE() {
		this.connMgr = new MKConnectionManagerE();
		this.connMgr.addMkEventListener(this);
		session = MKSessionE.getInstance();
		screenFactory = MKScreenFactoryProvider.getScreenFactory();
	}

	public static MKApplicationControllerE getInstance()
	{
		return instance;
	}

	public void init(MKMainAppE app, String[] args)
	{
		try {
			this.app = app;

			Logger.init(0x1bb4dc92b530325cL, "SocialBicing", MKConfigurationManagerE.PRINT_LOG, Constants.LOGGER_PATH);



			//LOCATION
			this.locationManager = LocationManager.getInstance();
			if (!this.locationManager.isStarted()) {

				GPSLocationProvider gpsP;
				RadioLocationProvider cellP;
				GpsConfiguration gpsConfig;
				RadioConfiguration radioConfig;

				radioConfig = new RadioConfiguration();
				radioConfig.geocoder = new GoogleGeocoder();
				radioConfig.queryFrecuency = (int)(30 * 1000);

				gpsConfig = new GpsConfiguration();             
				gpsConfig.queryFrecuency = (int)(10 * 1000);
				gpsConfig.fixPositionTimeout = 1000;
				gpsConfig.timeOutReattempt = 60 * 1000;

				gpsP = new GPSLocationProvider(gpsConfig);
				cellP = new RadioLocationProvider(radioConfig);


				locationManager.addProvider(cellP);
				locationManager.addProvider(gpsP);

				try {
					locationManager.start();
				} catch (StartException e) {
					Logger.log(e);                  
				}
			}

			mkEventHandler( new MKEvent(MKEventTypesE.DO_START_APP, args) );

		} catch (Exception e) {
			Logger.log(e);
			Logger.err("Ha ocurrido un error durante el inició de la aplicación. Vuelva a intentar iniciar, de persistir comuníquese con nosotros. Disculpe las molestias.", e.getMessage());
		}
	}

	public Object mkEventHandler(MKEvent event)
	{
		try
		{
			switch(event.getType())
			{

			case MKEventTypes.CLOSE_APP:{
				try{
					Logger.finish();
				}catch (Exception e) {}
				break;
			}
			case MKEventTypes.ERROR_OCURR:{

				String errorTitle = null;
				String errorBody = null;

				if(event.getData() != null){
					if(event.getData() instanceof Vector){
						Vector errores = (Vector) event.getData();
						if(errores.size() == 1){
							errorTitle = (errores.elementAt(0) == null)?null:errores.elementAt(0).toString();
						}
						else if(errores.size() == 2){
							errorTitle = (errores.elementAt(0) == null)?null:errores.elementAt(0).toString();
							errorBody = (errores.elementAt(1) == null)?null:errores.elementAt(0).toString();
						}
					}
					else if (event.getData() instanceof String){
						errorTitle = event.getData().toString();
					}
				}

				if(Functions.isEmpty(errorTitle) == true){
					errorTitle = "Se ha producido un error inesperado, volvé a intentar nuevamente por favor";
				}

				Logger.err(errorTitle, errorBody);

				//--Popeo la pantalla de loading en caso de estar presente
				while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
					app.popScreen(app.getActiveScreen());
				}
				break;
			}
			case MKEventTypes.CONNECTION_ERROR:
				Logger.log("#MKEventTypes.CONNECTION_ERROR");
				Dialog.alert("Error de conexión. Intente nuevamente más tarde" + ". Cod.Error: [" + (event.getData()!=null?(String)event.getData():"") + "]");
				//--Popeo la pantalla de loading en caso de estar presente
				while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
					app.popScreen(app.getActiveScreen());
				}
				break;
			case MKEventTypes.CONNECTION_TIME_OUT:
				String resDescripcion = (String)event.getData();
				Logger.log("#MKEventTypes.CONNECTION_TIME_OUT");
				Dialog.alert(resDescripcion);
				//--Popeo la pantalla de loading en caso de estar presente
				while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
					app.popScreen(app.getActiveScreen());
				}
				break;
			case MKEventTypesE.DO_START_APP:{

				Logger.log("MKEventTypes.START_APP");

				mkEventHandler( new MKEvent(MKEventTypesE.GO_SPLASH_SCREEN, null) );

				this.app.invokeLater(
						new Runnable(){
							public void run()
							{
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
								}
								mkEventHandler( new MKEvent(MKEventTypesE.GO_LOGIN_SCREEN, null) );
							}
						}
				);
				break;
			}
			case MKEventTypesE.GO_SPLASH_SCREEN:{
				Logger.log("MKEventTypesE.GO_SPLASH_SCREEN");

				SplashScreen splash = new SplashScreen();
				this.app.pushScreen(splash);
				this.app.requestForeground();
				break;
			}
			case MKEventTypesE.GO_BACK:{
				Logger.log("MKEventTypesE.GO_BACK");

				app.popScreen(app.getActiveScreen());
				while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
					app.popScreen(app.getActiveScreen());
				}
				break;
			}
			case MKEventTypesE.GO_LOGIN_SCREEN:
				Logger.log("#MKEventTypesE.GO_LOGIN_SCREEN");

				while(app.getScreenCount() > 0){
					app.popScreen(app.getActiveScreen());
				}

				app.pushScreen(currentScreen=screenFactory.createScreen(MKScreenTypesE.LOGIN));
				currentScreen.addMkEventListener(this);
				break;
			case MKEventTypesE.LOGIN_REQUEST:
				Logger.log("#MKEventTypesE.LOGIN_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);
				LoginRequest loginReq = (LoginRequest)event.getData();
				connMgr.doRequest( MKConnectionManagerE.LOGIN, loginReq );
				break;
			case MKEventTypesE.LOGIN_RESULT:{
				Logger.log("#MKEventTypesE.LOGIN_RESULT");
				LoginResult result = (LoginResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					Logger.log("sesion activa");

					AbsUsuario usuario = result.usuario;
					this.session.usuario = usuario;
					this.session.userStatus = result.userStatus;
					mkEventHandler( new MKEvent(MKEventTypesE.GET_FREE_BIKES_REQUEST, null) );
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.GET_FREE_BIKES_REQUEST:{
				Logger.log("#GET_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				Position pos = this.getBestLocation();
				IMKRequest request = new GetFreeBikesRequest(this.session.usuario.getId(), pos.getLatitude(), pos.getLongitude());
				connMgr.doRequest( MKConnectionManagerE.GET_FREE_BIKES, request );
				break;
			}
			case MKEventTypesE.GET_FREE_BIKES_RESULT:{
				Logger.log("#MKEventTypesE.GET_FREE_BIKES_RESULT");
				GetFreeBikesResult result = (GetFreeBikesResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					Vector bikes = result.bikes;
					mkEventHandler(new MKEvent(MKEventTypesE.GO_SEARCH_BIKESCREEN, bikes));
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.GET_BIKE_REQUEST:{
				Logger.log("#GET_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				int idBike = ((Integer)event.getData()).intValue();
				IMKRequest request = new GetBikeRequest(this.session.usuario.getId(), idBike);
				connMgr.doRequest( MKConnectionManagerE.GET_BIKE, request );
				break;
			}
			case MKEventTypesE.GET_BIKE_RESULT:{
				Logger.log("#MKEventTypesE.GET_BIKE_RESULT");
				GetBikeResult result = (GetBikeResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					Bici bike = result.bike;
					mkEventHandler( new MKEvent(MKEventTypesE.GO_BICI_SCREEN, bike) );
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.GO_FREE_BIKE_SCREEN:{
				Logger.log("#MKEventTypesE.GO_FREE_BIKE_SCREEN");
				Bici bike = (Bici) event.getData();

				currentScreen = new FreeBikeScreen(bike);
				app.pushScreen(currentScreen);
				currentScreen.addMkEventListener(this);
				break;
			}
			case MKEventTypesE.GO_CANCEL_RESERVATION_SCREEN:{
				Logger.log("#MKEventTypesE.GO_CANCEL_RESERVATION_SCREEN");
				Bici bike = (Bici) event.getData();

				currentScreen = new CancelReservationBikeScreen(bike);
				app.pushScreen(currentScreen);
				currentScreen.addMkEventListener(this);
				break;
			}
			case MKEventTypesE.GO_BICI_SCREEN:{
				Logger.log("#MKEventTypesE.GO_BICI_SCREEN");
				Bici bike = (Bici) event.getData();
				currentScreen = new BikeScreen(bike, this.session.usuario, this.session.userStatus);
				app.pushScreen(currentScreen);
				currentScreen.addMkEventListener(this);
				break;
			}
			case MKEventTypesE.GO_SEARCH_BIKESCREEN:{
				Logger.log("#MKEventTypesE.GO_SEARCH_BIKESCREEN");
//				Bici bike = (Bici) event.getData();
				Vector bikes = (Vector) event.getData();
				while(app.getScreenCount() > 0){
					app.popScreen(app.getActiveScreen());
				}
				currentScreen = new BikeSearchScreen(bikes);
				app.pushScreen(currentScreen);
				currentScreen.addMkEventListener(this);
				break;
			}
			case MKEventTypesE.GO_PASS_BIKE_SCREEN:{
				Logger.log("#MKEventTypesE.GO_PASS_BIKE_SCREEN");
				Bici bike = (Bici) event.getData();

				currentScreen = new PassBikeScreen(bike);
				app.pushScreen(currentScreen);
				currentScreen.addMkEventListener(this);
				break;
			}
			case MKEventTypesE.GO_DROP_BIKE_SCREEN:{
				Logger.log("#MKEventTypesE.GO_BICI_SCREEN");

				if(this.session.userStatus.getReserve() == null){
					Bici bike = (Bici) event.getData();
					this.mkEventHandler(new MKEvent(MKEventTypesE.RESERVE_BIKE_REQUEST, new Integer(bike.getId())));
				}
				else{
					currentScreen = new ReserveBikeScreen(this.session.userStatus.getReserve());
					app.pushScreen(currentScreen);
					currentScreen.addMkEventListener(this);
				}
				break;
			}
			case MKEventTypesE.RESERVE_BIKE_REQUEST:{
				Logger.log("#RESERVE_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				int idBike = ((Integer)event.getData()).intValue();
				IMKRequest request = new ReserveBikeRequest(this.session.usuario.getId(), idBike);
				connMgr.doRequest( MKConnectionManagerE.RESERVE_BIKE, request );
				break;
			}
			case MKEventTypesE.RESERVE_BIKE_RESULT:{
				Logger.log("#RESERVE_BIKE_RESULT");
				ReserveBikeResult result = (ReserveBikeResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					this.session.reserveCode = result.reserveCode;
					this.session.userStatus = result.userStatus;
					while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
						app.popScreen(app.getActiveScreen());
					}
					currentScreen = new ReserveBikeScreen(this.session.userStatus.getReserve());
					app.pushScreen(currentScreen);
					currentScreen.addMkEventListener(this);
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.USE_BIKE_REQUEST:{
				Logger.log("#USE_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				int idBike = ((Integer)event.getData()).intValue();
				IMKRequest request = new UseBikeRequest(this.session.usuario.getId(), idBike);
				connMgr.doRequest( MKConnectionManagerE.USE_BIKE, request );
				break;
			}
			case MKEventTypesE.USE_BIKE_RESULT:{
				Logger.log("#USE_BIKE_RESULT");
				UseBikeResult result = (UseBikeResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					//TODO: Lógica propia
					this.session.userStatus = result.userStatus;
					while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
						app.popScreen(app.getActiveScreen());
					}
					Dialog.inform("Operacion exitosa: " + MKEventTypesE.USE_BIKE_RESULT);
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.FREE_BIKE_REQUEST:{
				Logger.log("#FREE_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				FreeBikeRequest request = (FreeBikeRequest) event.getData();
				request.idUser = this.session.usuario.getId();
				
				connMgr.doRequest( MKConnectionManagerE.FREE_BIKE, request );
				break;
			}
			case MKEventTypesE.FREE_BIKE_RESULT:{
				Logger.log("#FREE_BIKE_RESULT");
				FreeBikeResult result = (FreeBikeResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					//TODO: Lógica propia
					this.session.userStatus = result.userStatus;
					while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
						app.popScreen(app.getActiveScreen());
					}
					Dialog.inform("Operacion exitosa: " + MKEventTypesE.FREE_BIKE_RESULT);
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.CANCEL_BIKE_RESERVATION_REQUEST:{
				Logger.log("#CANCEL_BIKE_RESERVATION_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				CancelBikeReservationRequest request = (CancelBikeReservationRequest) event.getData();
				request.idUser = this.session.usuario.getId();
				request.reserveCode = this.session.userStatus.getReserve().getReserveCode();
				connMgr.doRequest( MKConnectionManagerE.CANCEL_BIKE_RESERVATION, request );
				break;
			}
			case MKEventTypesE.CANCEL_BIKE_RESERVATION_RESULT:{
				Logger.log("#CANCEL_BIKE_RESERVATION_RESULT");
				CancelBikeReservationResult result = (CancelBikeReservationResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					//TODO: Lógica propia
					this.session.userStatus = result.userStatus;
					while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
						app.popScreen(app.getActiveScreen());
					}
					Dialog.inform("Operacion exitosa: " + MKEventTypesE.CANCEL_BIKE_RESERVATION_RESULT);
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			case MKEventTypesE.PASS_BIKE_REQUEST:{
				Logger.log("#PASS_BIKE_REQUEST");
				loadingScreen = (MKScreen) screenFactory.createScreen(MKScreenTypes.LOADING);
				app.pushScreen(loadingScreen);

				PassBikeRequest request = (PassBikeRequest) event.getData();
				request.idUser = this.session.usuario.getId();
				connMgr.doRequest( MKConnectionManagerE.PASS_BIKE, request );
				break;
			}
			case MKEventTypesE.PASS_BIKE_RESULT:{
				Logger.log("#PASS_BIKE_RESULT");
				PassBikeResult result = (PassBikeResult) event.getData();
				if(result.header.codigo==Constants.CODIGO_OK)
				{
					//TODO: Lógica propia
					this.session.userStatus = result.userStatus;
					while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
						app.popScreen(app.getActiveScreen());
					}
					Dialog.inform("Operacion exitosa!");
				}
				else{
					this.handleResultError(result.header.codigo, result.header.descripcion);
				}
				break;
			}
			}//switch
		}
		catch(Exception e)
		{
			Logger.log("#AppController Exception", e);
			Vector mensajes = new Vector();
			mensajes.addElement("Se ha producido un error inesperado, volvé a intentar nuevamente por favor");
			mensajes.addElement(e.getMessage());
			mkEventHandler( new MKEvent(MKEventTypes.ERROR_OCURR,  mensajes));
		}
		return event;

	}//mkEventHandler




	private void handleResultError(int codigo, String descripcion) {
		if(codigo != Constants.CODIGO_OK)
		{
			if(descripcion!=null){
				Dialog.alert(descripcion);
				while(app.getActiveScreen() instanceof Loading && app.getActiveScreen() != null){
					app.popScreen(app.getActiveScreen());
				}
			}
			else{
				Vector mensajes = new Vector();
				mensajes.addElement("Se ha producido un error inesperado, volvé a intentar nuevamente por favor");
				mkEventHandler( new MKEvent(MKEventTypes.ERROR_OCURR,  mensajes));
			}
		}
		else{
			Vector mensajes = new Vector();
			mensajes.addElement("Se ha producido un error inesperado, volvé a intentar nuevamente por favor");
			mkEventHandler( new MKEvent(MKEventTypes.ERROR_OCURR,  mensajes));
		}

	}


	public Position getBestLocation() {
		Position pos = this.locationManager.getCombinedLocation();
		if(pos == null){
			pos = new Position();
			pos.setLat(-34.5988);//-centro capFed
			pos.setLng(-58.4620);//-centro capFed
		}
		if(MKDevice.runningOnSimulator()){
			pos = new Position();
			pos.setLat(-34.5988);//-centro capFed
			pos.setLng(-58.4620);//-centro capFed
		}
		return pos;
	}
}