
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ui.fields.ImageButtonField;
import com.mobilenik.socialBicing.ws.PassBikeRequest;

public class PassBikeScreen extends MKScreenE
{
	private static Bitmap CONFIRMAR_BUTTON_FOCUS;
	private static Bitmap CONFIRMAR_BUTTON_UNFOCUS;
	
	private static Bitmap CANCELAR_BUTTON_FOCUS;
	private static Bitmap CANCELAR_BUTTON_UNFOCUS;
	
	private Bici bike;
	private EditField txtReserveCode;
	
	static{
		try {
			CONFIRMAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "confirmar-entrega_focus.png");
			CONFIRMAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "confirmar-entrega_unfocus.png");
			CONFIRMAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CONFIRMAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			CONFIRMAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CONFIRMAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			
			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_focus.png");
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_unfocus.png");
			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
		} catch (Exception e) {
		}
	}

	public PassBikeScreen(Bici bike){
		super();
		this.bike = bike;
		drawScreen();
	}

	protected void drawScreen()
	{
		if(this.vfm != null){
			this.vfm.deleteAll();
		}

		add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

//		vfm.add(new LabelField("Tiene " + Functions.timeStamToString(new Date(), new Date(this.reserve.getExpirationTime())) + " para confirmar la operación"));

		this.txtReserveCode = new EditField("Código:", null, 10, Field.FOCUSABLE);
		vfm.add(this.txtReserveCode);

		ImageButtonField btnConfirm = new ImageButtonField(null, Color.BLACK, CONFIRMAR_BUTTON_FOCUS, CONFIRMAR_BUTTON_UNFOCUS){
			public void onClick(){
				doPassBike();
			}
		};
//		vfm.add(btnConfirm);
		
		ImageButtonField btnCancel = new ImageButtonField(null, Color.BLACK, CANCELAR_BUTTON_FOCUS, CANCELAR_BUTTON_UNFOCUS){
			public void onClick(){
				doBack();
			}
		};
		
		HorizontalFieldManager hfm = new HorizontalFieldManager( Manager.HORIZONTAL_SCROLL_MASK & Manager.HORIZONTAL_SCROLLBAR & Manager.HORIZONTAL_SCROLLBAR_MASK);
		hfm.add(btnConfirm);
		hfm.add(btnCancel);
		vfm.add(hfm);
//		vfm.add(btnCancel);

		this.add(vfm);
	}

	
	private void doPassBike(){
		PassBikeRequest request = new PassBikeRequest(0, this.bike.getId(), this.txtReserveCode.getText());
		dispatchMkEvent (new MKEvent(MKEventTypesE.PASS_BIKE_REQUEST, request));		
	}

	private void doBack(){
		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_BACK, null));		
	}
}
