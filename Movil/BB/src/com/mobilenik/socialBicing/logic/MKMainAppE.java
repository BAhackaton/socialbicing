package com.mobilenik.socialBicing.logic;

import net.rim.blackberry.api.homescreen.HomeScreen;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.SystemListener;
import net.rim.device.api.ui.UiApplication;

import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.util.device.MKDevice;

/**
 * 
 */
public class MKMainAppE extends UiApplication implements /*IPushRegisterListener, IPushInboxListener,*/ SystemListener
{
	private boolean acceptsForeground = true;
	public static final String idAplicacion = "MBE";
//	private static int _pushType = PushMessageE.TYPE_EMPTY;

	public static void main(String[] args)
	{ 
		MKMainAppE app = new MKMainAppE();

		if(args.length>0 && args[0] != null && args[0].equals("startup")){
			app.startStartup();
		}else
		{//--puede ser IPushInbox.ENTRY_POINT_INBOX o "gui".
			app.startGUI(args);
		}
		app.enterEventDispatcher();
	}

	private MKMainAppE() 
	{    

	}

	private void startGUI(String[] args){

		MKApplicationControllerE.getInstance().init(this, args);
		/*
		Bitmap newBmp = Bitmap.getBitmapResource(MKScreenInfo.getInstance().getImgDir() + "appIconNew.png");
		Bitmap stdBmp = Bitmap.getBitmapResource(MKScreenInfo.getInstance().getImgDir() + "appIcon.png");
		EncodedImage appIconMini = EncodedImage.getEncodedImageResource(MKScreenInfo.getInstance().getImgDir() + "appMiniIcon.png");
		MKNotificationManager.createSingleton(newBmp, stdBmp, appIconMini);
		*/
		//SLM 23-Ene-08 Corrige perdida de foco en launch de aplicaciones
		requestForeground();
	}

	private void startStartup(){

		acceptsForeground = false;

		if( ApplicationManager.getApplicationManager().inStartup() ) {
            // Add a system listener to detect when system is ready and available.
			this.addSystemListener( this );
		}
		else{
			this.initializeAP();
		}
	}

	private void initializeAP() {
		
        try{
            invokeLater(new Runnable(){
                    public void run(){
                        changeHomeScreenIcon();
                        HomeScreen.setName(Constants.APP_NAME, 0);
                    }
            });
	    }catch(Exception e){
	            e.printStackTrace();
	    }
	}


	private void changeHomeScreenIcon(){

        String osVersion = MKDevice.getOsVersion();
        if(osVersion.compareTo("4.6.0") < 0){
                try {
                        Bitmap icon = Bitmap.getBitmapResource(MKScreenInfo.getInstance().getImgDir()+ Constants.IMG_APP_SMALL);

                        ApplicationManager appMgr = ApplicationManager.getApplicationManager();

                        // Setup the icons once Blackberry is able.
                        while(appMgr.inStartup()){ // Sleep until Blackberry has completed startup
                                try {
                                        Thread.sleep(1000);
                                }
                                catch (Exception ex) { }
                        }

                        if(icon != null){
                                try {
                                        HomeScreen.updateIcon(icon, 1);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                	} catch (Exception e) {
                        e.printStackTrace();
                }
        }
//        else{
//                auxIcon = Bitmap.getBitmapResource(MKScreenInfo.getInstance().getImgDir()+ Constants.IMG_APP);
//        }


//        try {
//                HomeScreen.setName( Constants.APP_DESKTOP_NAME, 1);
//        } catch (Exception e) {
//                e.printStackTrace();
//        }
}

	
	
	protected boolean acceptsForeground(){
		return this.acceptsForeground;
	}
	public void activate(){
		//--Handles foregrounding event. Quitamos ícono de novedad
		/*
		MKNotificationManager notificationMgr = MKNotificationManager.getInstance();
		if(notificationMgr!=null){
			notificationMgr.clean();
		}
		*/
		super.activate();
	}
/*
	public void onPushRegisterOk() {
		Logger.log("onPushRegisterOk");


		//--com.mobilenik.mobilebankingbb.empresas.push.EmpresasListDaemon.INBOX_FOLDER_ID
		long inboxFolderId = 0xf5eef35b42aa2eeeL;

		//--com.mobilenik.mobilebankingbb.empresas.push.EmpresasListDaemon.DELETED_FOLDER_ID       
		long deletedFolderId = 0x77d55407fe4c53f1L;

		//--obtengo la fuente del inbox (font default) para darle el mismo tamaño a los iconos
		Font font = Font.getDefault();
		int size = font.getHeight();

		//--Los tamaños de imagenes disponibles son 44, 32, 24, 20 y 16. Uilizo el de tamaño 
		//--inmediatamente inferior al tamaño de font
		//--Hasta OS 6 hay una restricción para el tamaño del icono. No puede superar los 32px
		try{
			String osVersion = DeviceInfo.getPlatformVersion();
			osVersion = osVersion.substring(0, 1);
			int intVersion = Integer.valueOf(osVersion).intValue();
			if(intVersion>=6){
				size = Math.min(size, 32);
			}
		}
		catch (Exception e) {
			Logger.log("No se pudo obtener la versión de SO", e);
		}

		if(size>=44) 
			size = 44;
		else if(size>=32)
			size = 32;
		else if(size>=24)
			size = 24;
		else if(size>=20)
			size = 20;
		else if(size>=16)
			size = 16;


		ApplicationIcon newIcon, readIcon, deletedIcon;

		String iconDir = MKScreenInfo.getInstance().getImgDir();

		EncodedImage encImgNew = EncodedImage.getEncodedImageResource(iconDir + "new" + size + ".png");
		EncodedImage encImgRead = EncodedImage.getEncodedImageResource(iconDir + "read" + size + ".png");
		EncodedImage encImgDeleted = EncodedImage.getEncodedImageResource(iconDir + "deleted" + size + ".png");

		newIcon = new ApplicationIcon(encImgNew, false);
		readIcon = new ApplicationIcon(encImgRead, false);
		deletedIcon = new ApplicationIcon(encImgDeleted, false);

		MessageListDaemon daemon = new MessageListDaemon("gui AS=e", this, inboxFolderId, deletedFolderId, newIcon, readIcon, deletedIcon);
		daemon.init();


		//		Thread pushDmn = new TestPushDaemon();
		//		Thread pushDmn = new PushDaemon(MKConfigurationManager.getConnectionSuffix());
		//		pushDmn.start();
	}

	public static int getPushType(){
		return _pushType;
	}

	public static void setPushTypeReaded() {
		_pushType = PushMessageE.TYPE_EMPTY;
	}
	public void messageOpenned(PushMessage message) {
		PushMessageE pushMessage = (PushMessageE)message;
		_pushType = pushMessage.getPushType();
	}
*/
	public void batteryGood() {}

	public void batteryLow() {}

	public void batteryStatusChange(int arg0) {}

	public void powerOff() {}

	public void powerUp() {
		this.removeSystemListener( this );
		this.initializeAP(); 
	}
} 