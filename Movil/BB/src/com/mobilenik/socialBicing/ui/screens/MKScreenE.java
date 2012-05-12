package com.mobilenik.socialBicing.ui.screens;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.mobilenik.core.ui.screens.MKScreen;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.core.ui.styles.GlobalStyles;
import com.mobilenik.util.device.Logger;

/**
 * como no hay herencia múltiple, creo un objeto dispatcher y escribo métodos puente
 */
public abstract class MKScreenE extends MKScreen
{
    protected GlobalStyles globalStyles = GlobalStyles.getInstance();
    protected static final int PADDING_2 = 8;
    protected static final int PADDING_1 = 4;
	private static final int BACKGROUND_COLOR = Color.YELLOW; // 0xF0F0F0;
    protected VerticalFieldManager vfm;
    protected int lastWidth = 0;
    protected Font screenFont;
    

    protected MKScreenE(long style){
    	screenFont = MKScreenInfo.getInstance().getDefaultFont();
        setFont(screenFont);
        vfm = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR){
            protected void sublayout(int width, int height)
            {
                super.sublayout(width - PADDING_2, height - PADDING_2);
                setExtent(width - PADDING_2, height - PADDING_2);
            }
    
        };
        vfm.setPadding(PADDING_2, 0, 0, PADDING_2);
    }
    
    protected MKScreenE()
    {
    	screenFont = MKScreenInfo.getInstance().getDefaultFont();
        setFont(screenFont);
        vfm = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR){
            protected void sublayout(int width, int height)
            {
                super.sublayout(width - PADDING_2, height - PADDING_2);
                setExtent(width - PADDING_2, height - PADDING_2);
            }
    
        };
        vfm.setPadding(PADDING_2, 0, 0, PADDING_2);
    }
    
    protected int getManagerWidth(){
         return  MKScreenInfo.getInstance().getScreenWidth() - PADDING_2 *2;
    }
    
    public void setData(Object data) {}
    
    protected abstract void drawScreen();
    
    protected void paintBackground(Graphics g){
		g.setBackgroundColor(BACKGROUND_COLOR);
		g.clear();
    }
    
    protected void onOrientationChange(int oldWidth, int newWitdh){
		try {
			this.deleteAll();
		} catch (Exception e) {
			Logger.log(e);
		}
		this.drawScreen();
	}
    
    
	protected void paint(Graphics graphics){

		graphics.setBackgroundColor(BACKGROUND_COLOR);
		graphics.clear();
		
		super.paint(graphics);

		int newWidth = MKScreenInfo.getInstance().getScreenWidth();
		if(this.lastWidth != 0 && this.lastWidth != newWidth){
			int lastWidthAux = this.lastWidth;
			saveScreenData();
			MKScreenInfo.getInstance().refresh();
			GlobalStyles.getInstance().refresh();
			this.onOrientationChange(lastWidthAux, newWidth);
		}
		this.lastWidth = newWidth;
	}
    

    protected void saveScreenData() {}

	protected void addTitle(String title){
    	addTitle(title, LabelField.NON_FOCUSABLE);
    }

    protected void addTitle(String title, long style){
    	LabelField lfTitle = new LabelField(title, style){
        	protected void paint(Graphics g){
        		g.setColor(0x963A19);
        		super.paint(g);
        	}
        };
        
        vfm.add(lfTitle);
    }
    
} 
