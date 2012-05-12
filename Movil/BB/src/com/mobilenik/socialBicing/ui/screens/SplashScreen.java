package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.mobilenik.core.common.Functions;
import com.mobilenik.core.ui.screens.MKScreen;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.util.device.Logger;


public class SplashScreen extends MKScreen{

	private Bitmap wallpaper;
	private VerticalFieldManager vfm;

	public SplashScreen() {
		super();
		this.wallpaper = Bitmap.getBitmapResource(MKScreenInfo.getInstance().getImgDir() + Constants.IMG_WALLPAPER);  

		this.drawScreen();
	}

	protected void drawScreen(){
		if(this.vfm != null){
			this.vfm.deleteAll();
			this.vfm = null;
		}

		try{
			this.wallpaper = Functions.resizeImageWidth_Antialiasing(this.wallpaper, MKScreenInfo.getInstance().getScreenWidth());
		}
		catch(Exception e){
			Logger.log(e);
		}

		this.vfm = new VerticalFieldManager( Field.USE_ALL_WIDTH | Field.USE_ALL_HEIGHT | Field.FIELD_HCENTER | Field.FIELD_VCENTER)
		{
			//Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				try{
//					graphics.setBackgroundColor(0xE3E3E3);
//					graphics.clear();
					MKScreenInfo mkScreenInfo = MKScreenInfo.getInstance();

					int width = mkScreenInfo.getScreenWidth();
					int height = mkScreenInfo.getScreenHeight();

					graphics.drawBitmap((width - wallpaper.getWidth())/2, (height - wallpaper.getHeight())/2, width, height, wallpaper, 0, 0);

					super.paint(graphics);
				}
				catch(Exception ex){
					Logger.err(58, ex);
				}
			}
		};

		this.add(this.vfm);
	}
	
	protected void onOrientationChange(int oldWidth, int newWitdh){
		try {
			this.deleteAll();
		} catch (Exception e) {
			Logger.log(e);
		}
		this.drawScreen();
	}
} 
