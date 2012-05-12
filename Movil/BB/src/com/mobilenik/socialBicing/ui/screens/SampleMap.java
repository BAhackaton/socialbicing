package com.mobilenik.socialBicing.ui.screens;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.logic.MKApplicationControllerE;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.util.location.Position;
import com.mobilenik.util.ui.map2.business.LatLng;
import com.mobilenik.util.ui.map2.business.LatLngBound;
import com.mobilenik.util.ui.map2.business.MapDimension;
import com.mobilenik.util.ui.map2.business.MapImages;
import com.mobilenik.util.ui.map2.business.Marker;
import com.mobilenik.util.ui.map2.fields.MapField;
import com.mobilenik.util.ui.map2.fields.MapField.IMapEventListener;
import com.mobilenik.util.ui.map2.fields.bubbleMarkers.DefaultBubblePainter;
import com.mobilenik.util.ui.map2.util.MapUtils;
import com.mobilenik.util.utiles.ImageManipulator;


public class SampleMap extends VerticalFieldManager implements IMapEventListener{


	private Marker _currentMarker1;
	private Vector _otherMarkers1;
	private IMapEventListener listener;

	
	public SampleMap(IMapEventListener listener){
		this.listener = listener;
	}
	
	public MapField getMap(Marker currentMarker1, Vector otherMarkers1){
		
		_currentMarker1 = currentMarker1;
		_otherMarkers1 = otherMarkers1;
		

		int fieldW = (Display.getWidth()*95)/100;
		int fieldH = (Display.getHeight()*80)/100;            


		String imgPath = "res/img/map/";
		Bitmap downloading = Bitmap.getBitmapResource(imgPath + "rojo.png");
		Bitmap downloadFailed = Bitmap.getBitmapResource(imgPath + "gris.png");
		Bitmap pushpinON = Bitmap.getBitmapResource(imgPath + "verde.png");
		Bitmap pushpinOFF = Bitmap.getBitmapResource(imgPath + "verde_unfocus.png");
		Bitmap pushpinCurrentON = Bitmap.getBitmapResource(imgPath + "azul.png");
		Bitmap pushpinCurrentOFF = Bitmap.getBitmapResource(imgPath + "azul_unfocus.png");

		Bitmap movementUpFocus = Bitmap.getBitmapResource(imgPath + "movementUp_focus.png");
        Bitmap movementUpUnFocus = Bitmap.getBitmapResource(imgPath + "movementUp_unfocus.png");
        Bitmap movementDownFocus = Bitmap.getBitmapResource(imgPath + "movementDown_focus.png");
        Bitmap movementDownUnFocus = Bitmap.getBitmapResource(imgPath + "movementDown_unfocus.png");
        Bitmap movementLeftFocus = Bitmap.getBitmapResource(imgPath + "movementLeft_focus.png");
        Bitmap movementLeftUnFocus = Bitmap.getBitmapResource(imgPath + "movementLeft_unfocus.png");
        Bitmap movementRightFocus = Bitmap.getBitmapResource(imgPath + "movementRight_focus.png");
        Bitmap movementRightUnFocus = Bitmap.getBitmapResource(imgPath + "movementRight_unfocus.png");

        Font font = getFont();
        int size = font.getHeight();
        
        movementUpFocus = ImageManipulator.scale(movementUpFocus, size, size);
        movementUpUnFocus = ImageManipulator.scale(movementUpUnFocus, size, size);
        movementDownFocus = ImageManipulator.scale(movementDownFocus, size, size);
        movementDownUnFocus = ImageManipulator.scale(movementDownUnFocus, size, size);
        movementLeftFocus = ImageManipulator.scale(movementLeftFocus, size, size);
        movementLeftUnFocus = ImageManipulator.scale(movementLeftUnFocus, size, size);
        movementRightFocus = ImageManipulator.scale(movementRightFocus, size, size);
        movementRightUnFocus = ImageManipulator.scale(movementRightUnFocus, size, size);

        
		MapImages mapImages = new MapImages(downloading, downloadFailed, pushpinON, pushpinOFF, pushpinCurrentON, pushpinCurrentOFF, movementUpFocus, movementUpUnFocus, movementDownFocus, movementDownUnFocus, movementLeftFocus, movementLeftUnFocus, movementRightFocus, movementRightUnFocus);

		LatLng center = new LatLng(currentMarker1.getLatitude(), currentMarker1.getLongitude());
		
		LatLngBound latLngBound = MapUtils.getBounds(fieldW, fieldH, center, MapUtils.ZOOM_LEVEL_MIN);
		MapDimension initialMapDimension = new MapDimension(latLngBound, MapUtils.ZOOM_LEVEL_MIN);

		Bitmap bmpDownloading = Bitmap.getBitmapResource(imgPath + "camera.png");
		Bitmap bmpFailed = Bitmap.getBitmapResource(imgPath + "image_failed.png");
		DefaultBubblePainter bubblePainter = new DefaultBubblePainter(bmpDownloading, bmpFailed);               

		MapField mapField = new MapField(bubblePainter, _otherMarkers1, _currentMarker1, fieldW, fieldH, mapImages/*, initialMapDimension, initialMap*/, this);
		Menu mapMenu = new SampleMenu(mapField, this);
		mapField.setMenu(mapMenu);
		return mapField;
	}

	public void onMarkerSelected(Marker marker) {
		listener.onMarkerSelected(marker);
	}

//	public Vector getOtherMarkers1() {
//		return _otherMarkers1;
//	}
//	
//
//	public Marker CurrentMarker1() {
//		return _currentMarker1;
//	}
	
}
