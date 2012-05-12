
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.mobilenik.core.common.Functions;
import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ui.fields.ImageButtonField;
import com.mobilenik.socialBicing.ws.LoginRequest;
import com.mobilenik.util.ui.fields.BorderField;
import com.mobilenik.util.ui.fields.ManagerLabelValue;

class LoginScreen extends MKScreenE
{

	private EditField inpUsuario;
	private PasswordEditField inpClave;
	private static final int BORDER_CONTROLS_COLOR = 0xCCCCCC;
	private static final int MIN_LENGTH_USER = 0;
	private static final int MAX_LENGTH_USER = 10;
	private static final int MIN_LENGTH_KEY = 0;
	private static final int MAX_LENGTH_KEY = 10;

	private String strUsuario = "";
	private String strClave = "";

	private static Bitmap LOGIN_BUTTON_FOCUS;
	private static Bitmap LOGIN_BUTTON_UNFOCUS;
	
	static{
		LOGIN_BUTTON_FOCUS = Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "ingresar-press.png");
		LOGIN_BUTTON_UNFOCUS = Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "ingresar-active.png");
	}

	public LoginScreen(){
		super();
		drawScreen();
	}

	protected void drawScreen()
	{
		if(this.vfm != null){
			this.vfm.deleteAll();
		}

		add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

		vfm.add(new LabelField());

		ManagerPrincipal mPpal = new ManagerPrincipal();
		vfm.add(mPpal);

		VerticalFieldManager mgrIngresar = new VerticalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER);

		ImageButtonField btnIngresar = new ImageButtonField(null, Color.BLACK, 
				LOGIN_BUTTON_FOCUS, 
				LOGIN_BUTTON_UNFOCUS){
			protected void onClick() {
				login();
			}
		};
		mgrIngresar.add(btnIngresar);

		vfm.add(new LabelField());

		vfm.add(mgrIngresar);

		add(vfm);

	}

	//	public void setData(Object data) 
	//	{  
	//		drawScreen();
	//	}

	private boolean validateLogin(){

		String user = inpUsuario.getText();
		if(user.length()<MIN_LENGTH_USER){
			Dialog.alert("El usuario debe tener un mínimo de" + " " + MIN_LENGTH_USER + " " + "caracteres");
			return false;
		}

		String key = inpClave.getText();
		if(key.length()<MIN_LENGTH_KEY){
			Dialog.alert("La clave debe tener un mínimo de" + " " + MIN_LENGTH_KEY + " " + "caracteres");
			return false;
		}

		return true;
	}



	//	protected void makeMenu(Menu menu, int instance)
	//	{
	//		super.makeMenu(menu, instance);
	//		//        menu.deleteAll();
	//
	//		int prio = 0;
	//
	//		MenuItem cmiLogin = new MenuItem("Ingresar", 0, prio++)
	//		{
	//			public void run(){          
	//				login();
	//			}
	//		};
	//
	//		menu.add(cmiLogin);
	//		menu.setDefault(cmiLogin);
	//		menu.add(MenuItem.separator(0));
	//	}


	protected void login(){

		if(validateLogin()){
			dispatchMkEvent (new MKEvent(MKEventTypesE.LOGIN_REQUEST, 
					new LoginRequest
					(
							inpUsuario.getText(),
							inpClave.getText()
					)
			)
			);
			inpClave.setText("");
		}
	}


	private class ManagerPrincipal extends Manager{

		ManagerUserKey mgrUserKey;
		//		ManagerCertificado mgrCertificate;

		public ManagerPrincipal() {
			super(USE_ALL_WIDTH);

			int width = (int)(getManagerWidth() * 0.75);
			mgrUserKey = new ManagerUserKey(width);
			add(mgrUserKey);

			//			mgrCertificate = new ManagerCertificado();
			//			add(mgrCertificate);
		}

		protected void sublayout(int width, int height) {

			int widthUserKey = (int)(getManagerWidth() * 0.75);
			int x = (getManagerWidth() - widthUserKey) / 2;
			int y = 0;
			layoutChild(mgrUserKey, widthUserKey, height);
			setPositionChild(mgrUserKey, x, y);
			x = 0;
			y = mgrUserKey.getPreferredHeight() + 10;
			//			layoutChild(mgrCertificate, width, height);
			//			setPositionChild(mgrCertificate, x, y);
			//			y = y + mgrCertificate.getPreferredHeight() + 10;
			setExtent(width, y);
		}
		
//		protected void paintBackground(Graphics g){
//
//			try{
//				Logger.log("MainScreen.paintBackground");
//				super.paintBackground(g);
//
//				if(BACKGROUND!=null){
//					g.drawBitmap(0,  0, BACKGROUND.getWidth(), BACKGROUND.getHeight(), BACKGROUND, 0, 0);
//				}
//			}
//			catch ( Exception ex ){
//				Logger.err(32, ex);
//			}
//		}
	}


	private class ManagerUserKey extends VerticalFieldManager{

		private final String strLblUser = "Usuario" + ": ";
		private final String strLblKey = "Clave" + ": ";

		public ManagerUserKey(final int width){
			super(VerticalFieldManager.FIELD_HCENTER);


			int widthLabel = getFont().getAdvance(strLblUser);
			final int widthValue = width-widthLabel;

			inpUsuario = new EditField(null, strUsuario, MAX_LENGTH_USER, Field.FOCUSABLE){

				public int getPreferredWidth(){
					return widthValue-BorderField.getBorder2()*2;//--le resto el padding que es el del border del value
				}
				protected void displayFieldFullMessage(){}
			};

			ManagerLabelValue mlvUser = new ManagerLabelValue(strLblUser, inpUsuario);
			mlvUser.setBorderColor(BORDER_CONTROLS_COLOR);


			inpClave= new PasswordEditField(null, strClave, MAX_LENGTH_KEY, Field.FOCUSABLE){

				public int getPreferredWidth(){
					return widthValue-BorderField.getBorder2()*2;//--le resto el padding que es el del border del value 
				}
				protected void displayFieldFullMessage(){}
			};
			inpClave.setFont(globalStyles.fontXXLPlain);

			ManagerLabelValue mlvKey = new ManagerLabelValue(strLblKey, inpClave);
			mlvKey.setBorderColor(BORDER_CONTROLS_COLOR);

			add(mlvUser);
			add(new LabelField());
			add(mlvKey);
		}
	}

	protected void saveScreenData() {
		strUsuario = inpUsuario.getText(); 
		strClave = inpClave.getText(); 
	}

}
