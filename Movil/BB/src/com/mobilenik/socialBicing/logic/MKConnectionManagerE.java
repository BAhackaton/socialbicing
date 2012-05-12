package com.mobilenik.socialBicing.logic;

import java.io.ByteArrayInputStream;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.mobilenik.core.logic.MKConfigurationManager;
import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.logic.events.MKEventTypes;
import com.mobilenik.core.web.HTTPLoader;
import com.mobilenik.core.web.RequestEvent;
import com.mobilenik.core.ws.IMKRequest;
import com.mobilenik.core.ws.IMKResult;
import com.mobilenik.core.ws.MKConnectionManager;
import com.mobilenik.socialBicing.ws.CancelBikeReservationResult;
import com.mobilenik.socialBicing.ws.FreeBikeResult;
import com.mobilenik.socialBicing.ws.GetBikeResult;
import com.mobilenik.socialBicing.ws.GetFreeBikesResult;
import com.mobilenik.socialBicing.ws.LoginResult;
import com.mobilenik.socialBicing.ws.PassBikeResult;
import com.mobilenik.socialBicing.ws.ReserveBikeResult;
import com.mobilenik.socialBicing.ws.UseBikeResult;
import com.mobilenik.util.device.Logger;

public class MKConnectionManagerE extends  MKConnectionManager
{
	//wsActions
	public static final int LOGIN  = 2;
	public static final int GET_FREE_BIKES = 3;
	public static final int GET_BIKE = 4;
	public static final int RESERVE_BIKE = 5;
	public static final int USE_BIKE = 6;
	public static final int FREE_BIKE = 7;
	public static final int CANCEL_BIKE_RESERVATION = 8;
	public static final int PASS_BIKE = 9;

	public MKConnectionManagerE(){
		url = MKConfigurationManagerE.getWSServerE();
		httpLoader.addMkEventListener(this);
	}


	public Object mkEventHandler(MKEvent event)
	{
		this.event = event;

		UiApplication.getUiApplication().invokeLater
		(
				new Runnable() 
				{ 
					public void run() 
					{ 
						notifyAppController();
					}        
				}
		);
		return event;
	}

	protected void notifyAppController()
	{
		Logger.log("#result = "+event.getData());

		switch(event.getType())
		{           
		case MKEventTypes.HTTP_LOADER_COMPLETE:

			IMKResult result = null;
			MKEvent resultEvent = null;

			//fabrico result y resultEvent
			switch(((RequestEvent) event).requestId)
			{

			case LOGIN:
				result = new LoginResult();
				resultEvent = new MKEvent(MKEventTypesE.LOGIN_RESULT, null);
				break;

			case GET_BIKE:
				result = new GetBikeResult();
				resultEvent = new MKEvent(MKEventTypesE.GET_BIKE_RESULT, null);
				break;
			case GET_FREE_BIKES:
				result = new GetFreeBikesResult();
				resultEvent = new MKEvent(MKEventTypesE.GET_FREE_BIKES_RESULT, null);
				break;
			case CANCEL_BIKE_RESERVATION:
				result = new CancelBikeReservationResult();
				resultEvent = new MKEvent(MKEventTypesE.CANCEL_BIKE_RESERVATION_RESULT, null);
				break;
			case PASS_BIKE:
				result = new PassBikeResult();
				resultEvent = new MKEvent(MKEventTypesE.PASS_BIKE_RESULT, null);
				break;
			case RESERVE_BIKE:
				result = new ReserveBikeResult();
				resultEvent = new MKEvent(MKEventTypesE.RESERVE_BIKE_RESULT, null);
				break;
			case USE_BIKE:
				result = new UseBikeResult();
				resultEvent = new MKEvent(MKEventTypesE.USE_BIKE_RESULT, null);
				break;
			case FREE_BIKE:
				result = new FreeBikeResult();
				resultEvent = new MKEvent(MKEventTypesE.FREE_BIKE_RESULT, null);
				break;
			default:
				return;
			}

			try
			{
				//creo XMLDom
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				Document docResult = builder.parse( new ByteArrayInputStream( ((String)event.getData()).getBytes("UTF-8") ) );

				result.parse(docResult);
				resultEvent.setData(result);
				dispatchMkEvent(resultEvent);
			}
			catch(Exception e)
			{
				Logger.log("#Document creation  ERROR "+e);
				dispatchMkEvent( new MKEvent(MKEventTypes.CONNECTION_ERROR, e.getMessage()) );

			}
			break;
		case MKEventTypes.HTTP_LOADER_ERROR:
			Logger.log("#HTTP_LOADER_ERROR");
			dispatchMkEvent( new MKEvent(MKEventTypes.CONNECTION_ERROR, ((HTTPLoader)event.getData()).error) );
			break;
		}
	}

	protected boolean getTestMode() {
		return MKConfigurationManager.TEST_MODE;
	}

	protected void TEST_doRequest(IMKRequest request, int action) {
		//		String testResponse = "";
		//
		//		String path = "/res/test/empresas/wsdl/";
		//		switch(action)
		//		{
		//		case LOGIN:
		//			testResponse = getResourceAsString(path+"login.xml");
		//			break;
		//		case OPERACIONES:
		//			OperacionesRequest operReq = (OperacionesRequest)request;
		//			String fullPath = "";
		//			if(operReq.backPage!=null){
		//				lastPage = lastPage-1;
		//			}
		//			else if(operReq.nextPage!=null){
		//				lastPage = lastPage+1;
		//			}
		//			else{
		//				lastPage = 1;
		//			}
		//			fullPath = path + "operaciones_page" + lastPage + ".xml";
		//			testResponse = getResourceAsString(fullPath);
		//			break;
		//		case OPERACION_AUTORIZAR:
		//			if(autorizar_ok){
		//				testResponse = getResourceAsString(path+"operacionAutorizar.xml");
		//				autorizar_ok = false;
		//			}
		//			else{
		//				testResponse = getResourceAsString(path+"operacionAutorizar_error.xml");
		//				autorizar_ok = true;
		//			}
		//			break;
		//		}
		//		if(!Functions.isEmpty(testResponse)){
		//			httpLoader.TEST_sendAndLoad(testResponse, action);
		//		}
	}

	////----////
}
