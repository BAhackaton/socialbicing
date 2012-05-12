package com.mobilenik.socialBicing.ui.fields;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

import com.mobilenik.util.device.Logger;

public class ImageButtonField extends Field{

	private Bitmap curImage, imageFocus, imageUnfocus;
	private String text;
	private int textColor = -1;

	public ImageButtonField(String text, int textColor, Bitmap imageFocus, Bitmap imageUnfocus) {
		super(Field.USE_ALL_WIDTH);
		this.text = text;
		this.imageFocus = imageFocus;
		this.imageUnfocus = imageUnfocus;
		this.curImage = this.imageUnfocus;
		this.textColor = textColor;
	}

	protected void layout(int width, int height) {
		this.setExtent(this.getPreferredWidth(), this.getPreferredHeight());
	}

	public int getPreferredWidth(){
		return curImage.getWidth();
	}

	public int getPreferredHeight(){
		int ret = 0;
		if(curImage!=null){
			ret = curImage.getHeight();
		}
		return ret;
	}

	protected boolean trackwheelClick(int status, int time) {
		this.onClick();                    
		return true;
	}

	protected void onClick(){}

	public boolean isFocusable(){
		return true;
	}

	protected void onUnfocus() {
		curImage = imageUnfocus;
		invalidate();
	}

	protected void onFocus(int arg) {
		curImage = imageFocus;
		invalidate();
	}

	protected void paint(Graphics g){
		try {
			g.drawBitmap(0,  0, getPreferredWidth(), getPreferredHeight(), curImage, 0, 0);
			if(com.mobilenik.util.common.Functions.isEmpty(this.text) == false){
				Font f = g.getFont();	

				int x = (this.getPreferredWidth() - f.getAdvance(this.text)) / 2;
				int y = (int) ((this.getPreferredHeight() - f.getHeight()) / 2.5);
				
				if(this.textColor != -1){
					g.setColor(this.textColor);
				}
				g.drawText(this.text, x, y);
			}
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	protected void drawFocus(Graphics arg0, boolean arg1) {}

} 
