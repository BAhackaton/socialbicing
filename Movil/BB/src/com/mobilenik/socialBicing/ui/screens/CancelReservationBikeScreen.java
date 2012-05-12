
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.EditField;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.fields.CommandButton;
import com.mobilenik.core.ws.IMKRequest;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ws.CancelBikeReservationRequest;

public class CancelReservationBikeScreen extends MKScreenE
{
	private Bici bike;
	private EditField txtReason;
	public CancelReservationBikeScreen(Bici bike){
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

		this.txtReason = new EditField("Motivo", null, 10, Field.FOCUSABLE);
		vfm.add(this.txtReason);
		
		CommandButton btnCancel = new CommandButton("Cancelar"){
			public void onClick(){
				doCancelReservation();
			}
		};

		vfm.add(btnCancel);

		this.add(vfm);
	}

	private void doCancelReservation(){
		IMKRequest request = new CancelBikeReservationRequest(0, this.bike.getId(), null, this.txtReason.getText());
		dispatchMkEvent (new MKEvent(MKEventTypesE.CANCEL_BIKE_RESERVATION_REQUEST, request));		
	}
}
