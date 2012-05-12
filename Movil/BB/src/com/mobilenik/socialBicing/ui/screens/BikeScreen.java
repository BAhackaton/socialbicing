
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.LabelValueManager;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.Functions;
import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.EstadoBici;
import com.mobilenik.socialBicing.common.biz.UserStatus;
import com.mobilenik.socialBicing.logic.MKApplicationControllerE;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ui.fields.ImageButtonField;
import com.mobilenik.util.location.Position;

public class BikeScreen extends MKScreenE
{
	private static Bitmap RESERVE_BUTTON_FOCUS;
	private static Bitmap RESERVE_BUTTON_UNFOCUS;

	private static Bitmap LIBERAR_BUTTON_FOCUS;
	private static Bitmap LIBERAR_BUTTON_UNFOCUS;

	private static Bitmap CANCELAR_BUTTON_FOCUS;
	private static Bitmap CANCELAR_BUTTON_UNFOCUS;

	private static Bitmap USAR_BUTTON_FOCUS;
	private static Bitmap USAR_BUTTON_UNFOCUS;

	private static Bitmap PASAR_BUTTON_FOCUS;
	private static Bitmap PASAR_BUTTON_UNFOCUS;

	private static final int BACKGROUND_COLOR_1 = 0xFFE460;
	private static final int BACKGROUND_COLOR_2 = 0xFFDD3C;

	private int alternateColor = 0;
	private Bici bike;
	private AbsUsuario user;
	private UserStatus userStatus;

	static{
		try {
			USAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "usar-bici_focus.png");
			USAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "usar-bici_unfocus.png");
			USAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(USAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			USAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(USAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));

			LIBERAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "liberar-bici_focus.png");
			LIBERAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "liberar-bici_unfocus.png");
			LIBERAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(LIBERAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			LIBERAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(LIBERAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));

			RESERVE_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "reservar-bici_focus.png");
			RESERVE_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "reservar-bici_unfocus.png");
			RESERVE_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(RESERVE_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			RESERVE_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(RESERVE_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));

			PASAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "confirmar-entrega_focus.png");
			PASAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "confirmar-entrega_unfocus.png");
			PASAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(PASAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			PASAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(PASAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));

			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_focus.png");
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_unfocus.png");
			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
		} catch (Exception e) {
		}
	}

	public BikeScreen(Bici bike, AbsUsuario user, UserStatus userStatus){
		super();
		this.bike = bike;
		this.user = user;
		this.userStatus = userStatus;

		drawScreen();
	}

	protected void drawScreen()
	{
		add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

		
		Position currentPosition = MKApplicationControllerE.getInstance().getBestLocation();
		String distanceValue;
		if(currentPosition.equals(Position.EMPTY_POSITION) == true){
			distanceValue = "-";
		}
		else{
			double distance = Functions.getDistances(this.bike.getLatLng().getLat(), this.bike.getLatLng().getLng(), currentPosition.getLatitude(), currentPosition.getLongitude());
			distanceValue = Functions.distanceToString(distance);
		}

		String responsableName;
		switch(this.bike.getResponsable()){
		case 3: responsableName = "Nacho"; break;
		case 5: responsableName = "Pedro";break;
		case 7: responsableName = "Jose";break;
		case 9: responsableName = "María";break;
		default : responsableName = "Leandro";break;
		}
		
		this.vfm.add(this.createLine("Estado:", EstadoBici.toString(bike.getEstado())));
		this.vfm.add(this.createLine("Código:", bike.getCode()));
		this.vfm.add(this.createLine("Ubicación:", bike.getCalle()));
		this.vfm.add(this.createLine("Distancia:", distanceValue));
		this.vfm.add(this.createLine("Responsable:", responsableName));
		this.vfm.add(new LabelField());

		ImageButtonField btnReservar = new ImageButtonField(null, Color.BLACK, RESERVE_BUTTON_FOCUS, RESERVE_BUTTON_UNFOCUS){
			public void onClick(){
				doReserveBike();
			}
		};

		ImageButtonField btnLiberar = new ImageButtonField(null, Color.BLACK, LIBERAR_BUTTON_FOCUS, LIBERAR_BUTTON_UNFOCUS){
			public void onClick(){
				doFreeBike();
			}
		};

		ImageButtonField btnUsar = new ImageButtonField(null, Color.BLACK, USAR_BUTTON_FOCUS, USAR_BUTTON_UNFOCUS){
			public void onClick(){
				doUseBike();
			}
		};

		ImageButtonField btnCancelReservation = new ImageButtonField(null, Color.BLACK, CANCELAR_BUTTON_FOCUS, CANCELAR_BUTTON_UNFOCUS){
			public void onClick(){
				doCancelReservation();
			}
		};

		ImageButtonField btnPassBike = new ImageButtonField(null, Color.BLACK, PASAR_BUTTON_FOCUS, PASAR_BUTTON_UNFOCUS){
			public void onClick(){
				doPassBike();
			}
		};
		HorizontalFieldManager hfm = new HorizontalFieldManager(Manager.HORIZONTAL_SCROLL_MASK & Manager.HORIZONTAL_SCROLLBAR & Manager.HORIZONTAL_SCROLLBAR_MASK);

		if(this.bike.getResponsable() == this.user.getId()
				&& this.bike.getEstado() == EstadoBici.STATE_FREE ){
			hfm.add(btnUsar);
		}

		if(this.bike.getResponsable() != this.user.getId()
				&& this.bike.getEstado() == EstadoBici.STATE_FREE ){
			hfm.add(btnReservar);
		}

		if(this.bike.getResponsable() == this.user.getId()
				&& this.bike.getEstado() == EstadoBici.STATE_IN_USE){
			hfm.add(btnLiberar);
		}

		if(userStatus.getReserve() != null
				&& userStatus.getReserve().getIdBike() ==  this.bike.getId()
				&& this.bike.getEstado() == EstadoBici.STATE_RESERVED){
			hfm.add(btnCancelReservation);
		}

		if(this.bike.getResponsable() == this.user.getId()
				&& this.bike.getEstado() == EstadoBici.STATE_RESERVED){
			hfm.add(btnPassBike);
		}

		this.vfm.add(hfm);
		this.add(vfm);
	}

	private void doReserveBike(){
		dispatchMkEvent(new MKEvent(MKEventTypesE.GO_DROP_BIKE_SCREEN, this.bike));
	}

	private void doFreeBike(){
		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_FREE_BIKE_SCREEN, this.bike));
	}

	private void doUseBike(){
		dispatchMkEvent (new MKEvent(MKEventTypesE.USE_BIKE_REQUEST, new Integer(this.bike.getId())));
	}

	private void doCancelReservation(){

		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_CANCEL_RESERVATION_SCREEN, this.bike));
	}

	private void doPassBike(){
		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_PASS_BIKE_SCREEN, this.bike));
	}

	private Field createLine(String label, String value) {
		Field field;
		int backgroundColor;
		Font labelFont = screenFont.derive(Font.BOLD);

		backgroundColor = alternateColor%2==0 ? BACKGROUND_COLOR_1 : BACKGROUND_COLOR_2;
		this.alternateColor++;

		MyLabelField mlfLabel = new MyLabelField(label, Field.NON_FOCUSABLE, labelFont);
		LabelField lfValue = new LabelField(value, Field.FOCUSABLE);

		field = new MyLabelValueManager(mlfLabel, lfValue, backgroundColor);
		return field;
	}
	private class MyLabelField extends LabelField{

		public MyLabelField(String text, long style, Font font){
			super(text, style);
			setFont(font);
		}
	}

	private class MyLabelValueManager extends LabelValueManager{
		public static final int VALUE_ALIGN_RIGHT = 1;
		public static final int VALUE_ALIGN_LEFT = 2;
		private int valueAlign = VALUE_ALIGN_RIGHT;

		public MyLabelValueManager(Field label, Field value, int backgroundColor, int valueAlign) {
			super(label, value, backgroundColor);
			this.valueAlign = valueAlign;
		}

		public MyLabelValueManager(MyLabelField mlfLabel, LabelField lfValue, int backgroundColor) {
			super(mlfLabel, lfValue, backgroundColor);
		}

		protected void sublayout(int width, int height)
		{
			int totalHeight; 
			layoutChild(label, width, height);
			setPositionChild(label, 0, 0);

			layoutChild(value, width, height);
			int vw = value.getPreferredWidth();
			if(label.getPreferredWidth() == 0 && valueAlign==VALUE_ALIGN_LEFT){
				setPositionChild(value, 0, 0);
				totalHeight = value.getHeight() + 1;
			}
			else if(label.getPreferredWidth() + vw > width ){
				setPositionChild(value, Math.max(0, width - vw), label.getPreferredHeight() + 1);
				totalHeight = label.getPreferredHeight() + value.getHeight() + 1;
			}
			else{
				setPositionChild(value, width - vw, 0);
				totalHeight = Math.max(label.getPreferredHeight(),value.getPreferredHeight()) + 1;
			}


			setExtent(width, totalHeight );
		}

		public void paint(Graphics g){
			if(backgroundColor!=NO_COLOR){
				g.setColor(backgroundColor);
				g.fillRoundRect(0,0, this.getPreferredWidth(), this.getPreferredHeight(), 5, 10);
				g.setColor(Color.BLACK);
//				g.setBackgroundColor(backgroundColor);
//				g.clear();
			}
			super.paint(g);
		}
	}	
}