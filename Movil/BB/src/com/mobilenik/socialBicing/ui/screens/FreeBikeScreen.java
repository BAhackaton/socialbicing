
package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.ui.component.LabelField;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.fields.CommandButton;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.LatLng;
import com.mobilenik.socialBicing.common.biz.Place;
import com.mobilenik.socialBicing.logic.MKApplicationControllerE;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.ws.FreeBikeRequest;
import com.mobilenik.util.location.Position;

public class FreeBikeScreen extends MKScreenE
{
	private Bici bike;
	public FreeBikeScreen(Bici bike){
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

		vfm.add(new LabelField("Ubicación: Mi Posición"));
		
		CommandButton btnFreeBike = new CommandButton("Liberar"){
			public void onClick(){
				doFreeBike();
			}
		};

		vfm.add(btnFreeBike);

		this.add(vfm);
	}

	private void doFreeBike(){
		Position pos = MKApplicationControllerE.getInstance().getBestLocation();
		Place place = new Place("Cabildo 500", new LatLng(pos.getLatitude(), pos.getLongitude()));
		dispatchMkEvent (new MKEvent(MKEventTypesE.FREE_BIKE_REQUEST, new FreeBikeRequest(place, 0, this.bike.getId())));		
	}
}
