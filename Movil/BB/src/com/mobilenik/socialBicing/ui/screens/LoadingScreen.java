package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.fields.AnimatedGIFField;
import com.mobilenik.core.ui.screens.Loading;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.util.device.Logger;

class LoadingScreen extends Loading
{
	private static final int BACKGROUND_COLOR = 0xF0F0F0;
	
	protected void drawScreen()
	{
		Logger.log("LoadingScreen.drawScreen()");

        add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

		add(new LabelField()); //espacio
		//add(new LabelField(_resources.getString(WAIT), Field.FIELD_HCENTER));
		add(new LabelField("Cargando...", Field.FIELD_HCENTER));
		add(new LabelField()); //espacio

		try {
			String fileName = getIconDir() + "waitGif.bin";

			EncodedImage img = EncodedImage.getEncodedImageResource(fileName);

			GIFEncodedImage gif = (GIFEncodedImage) img;
			add(new AnimatedGIFField(gif, Field.FIELD_HCENTER | Field.FIELD_VCENTER, true));
		} catch (Exception e) {
			Logger.log("No se pudo mostrar la animacion. " + e.getMessage());
		}
	}

	protected void onOrientationChange(int oldWidth, int newWitdh){
		try {
			this.deleteAll();
		} catch (Exception e) {
			Logger.log(e);
		}
		this.drawScreen();
	}
	
    protected void paintBackground(Graphics g){
		g.setBackgroundColor(BACKGROUND_COLOR);
		g.clear();
    }
}
