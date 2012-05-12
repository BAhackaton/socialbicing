
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.Functions;
import com.mobilenik.socialBicing.common.biz.Reserva;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ui.fields.ImageButtonField;

public class ReserveBikeScreen extends MKScreenE
{
	private static Bitmap CANCELAR_BUTTON_FOCUS;
	private static Bitmap CANCELAR_BUTTON_UNFOCUS;

	private LabelField txtTime;
	private Reserva reserve;
	private boolean timerEnable = true;

	static{
		try {
			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_focus.png");
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "cancelar-reserva_unfocus.png");
			CANCELAR_BUTTON_FOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_FOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
			CANCELAR_BUTTON_UNFOCUS = com.mobilenik.core.common.Functions.resizeImageWidth_Antialiasing(CANCELAR_BUTTON_UNFOCUS, (int) (MKScreenInfo.getInstance().getScreenWidth() /5));
		} catch (Exception e) {
		}

	}
	
	public ReserveBikeScreen(Reserva reserve){
		super();
		this.reserve = reserve;
		drawScreen();
	}

	protected void drawScreen()
	{
		if(this.vfm != null){
			this.vfm.deleteAll();
		}

		add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

		this.txtTime = new LabelField("Tiempo restante: " + Functions.timeStamToString(System.currentTimeMillis(), this.reserve.getExpirationTime()));
		vfm.add(this.txtTime);
		
		Thread t = new Thread(new Runnable() {

			public void run() {
				while(timerEnable == true){
					try {
						Thread.sleep(1000);
						 UiApplication.getUiApplication().invokeLater(new Runnable()
	                 		{
	                 			public void run()
	                 			{
	                 				try{
	                 					String value = "Tiempo restante: " + Functions.timeStamToString(System.currentTimeMillis(), reserve.getExpirationTime());
	            						txtTime.setText(value);
	                 				}
	                 				catch (Exception e) {}
	                 			}
	                 		});
					} catch (InterruptedException e) {}
				}
			}
		});
		t.start();

		ImageButtonField btnCancel = new ImageButtonField(null, Color.BLACK, CANCELAR_BUTTON_FOCUS, CANCELAR_BUTTON_UNFOCUS){
			public void onClick(){
				doCancelReservation();
			}
		};

		vfm.add(btnCancel);

		vfm.add(new LabelField("Código: " + this.reserve.getReserveCode()));

		this.add(vfm);
	}

	private void doCancelReservation(){
		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_CANCEL_RESERVATION_SCREEN, this.reserve.getBike()));		
	}
}
