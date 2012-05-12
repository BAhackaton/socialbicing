package com.mobilenik.socialBicing.ui.screens;

import java.util.Vector;


import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;

import com.mobilenik.util.ui.map2.business.Marker;
import com.mobilenik.util.ui.map2.fields.MapField;
import com.mobilenik.util.ui.map2.logic.ZoomMgr;
import com.mobilenik.util.ui.map2.screens.AmountZoomScreen;
import com.mobilenik.util.ui.map2.screens.SplitScreen;
import com.mobilenik.util.ui.map2.screens.AmountZoomScreen.AmountZoomListener;
import com.mobilenik.util.ui.map2.screens.SplitScreen.SplitListener;

public class SampleMenu 
	extends Menu 
	implements AmountZoomListener, SplitListener{
	

	private MapField _mapField;
	private SampleMap _sampleMap;

	public SampleMenu(MapField mapField, SampleMap sampleMap){
		super(Menu.INSTANCE_DEFAULT);
		_mapField = mapField;
		_sampleMap = sampleMap;
		makeMenu();
	}
	
	private void makeMenu() {

		
		int idx = 0;
		
		deleteAll();

//		add(new MenuItem("Ver nivel de zoom", idx++, 0) {
//			public void run() {
//				try {
//					int currentZoom = _mapField.getCurrentZoom();
//					Dialog.alert("Zoom: " + currentZoom);
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Ver nivel de zoom. " +  ex.getMessage());
//				}
//			}
//		} );
//		add(new MenuItem("Aumentar zoom", idx++, 0) {
//			public void run() {
//				try {
//					int currentZoom = _mapField.getCurrentZoom();
//					boolean success = ZoomMgr.verifyZoomMovement(currentZoom, 1, ZoomMgr.MODE_ZOOM_IN);
//					if(success){
//						onZoomChanged(currentZoom+1);
//					}
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Aumentar zoom. " +  ex.getMessage());
//				}
//			}
//		} );
//		add(new MenuItem("Disminuir zoom", idx++, 0) {
//			public void run() {
//				try {
//					int currentZoom = _mapField.getCurrentZoom();
//					boolean success = ZoomMgr.verifyZoomMovement(currentZoom, 1, ZoomMgr.MODE_ZOOM_OUT);
//					if(success){
//						onZoomChanged(currentZoom-1);
//					}
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Disminuir zoom. " +  ex.getMessage());
//				}
//			}
//
//		} );
//		add(new MenuItem("Super aumentar zoom", idx++, 0) {
//			public void run() {
//				try {
//					showAmountZoomScreen(ZoomMgr.MODE_ZOOM_IN);
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Super aumentar zoom. " +  ex.getMessage());
//				}
//			}
//		} );
//		add(new MenuItem("Super disminuir zoom", idx++, 0) {
//			public void run() {
//				try {
//					showAmountZoomScreen(ZoomMgr.MODE_ZOOM_OUT);
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Super disminuir zoom. " +  ex.getMessage());
//				}
//			}
//		} );
//		add(new MenuItem("Desplazar derecha", idx++, 0) {
//			public void run() {
//				try {
//					_mapField.moveRelativeRight(1);	
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al desplazar a la derecha. " +  ex.getMessage());
//				}
//			}
//		} );
//		
//		add(new MenuItem("Desplazar izquierda", idx++, 0) {
//			public void run() {
//				try {
//					_mapField.moveRelativeLeft(1);	
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al desplazar a la izquierda. " +  ex.getMessage());
//				}
//			}
//		} );
//
//		add(new MenuItem("Desplazar arriba", idx++, 0) {
//			public void run() {
//				try {
//					_mapField.moveRelativeUp(1);	
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al desplazar arriba. " +  ex.getMessage());
//				}
//			}
//		} );
//
//		add(new MenuItem("Desplazar abajo", idx++, 0) {
//			public void run() {
//				try {
//					_mapField.moveRelativeDown(1);	
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al desplazar abajo. " +  ex.getMessage());
//				}
//			}
//		} );

//		add(new MenuItem("Set split", idx++, 0) {
//			public void run() {
//				try {
//					showSplitScreen();
//				}
//				catch(Exception ex){
//					System.out.println("ERROR en Set split. " +  ex.getMessage());
//				}
//			}
//
//		} );
		
//		add(new MenuItem("Detener descargas", idx++, 0) {
//			public void run() {
//				try {
//					_mapField.stopDownloads();
//					Dialog.alert("Descargas detenidas");
//				}
//				catch(Exception ex){
//					System.out.println("ERROR Detener descargas. " +  ex.getMessage());
//				}
//			}
//
//		} );
		
		add(new MenuItem("Ir a mi ubicación", idx++, 0) {
			public void run() {
				try {
					_mapField.goToCurrentMarker();
				}
				catch(Exception ex){
					System.out.println("ERROR en Ir a mi ubicación. " +  ex.getMessage());
				}
			}

		} );
		
//		add(new MenuItem("Markers Cap Fed", idx++, 0) {
//			public void run() {
//				try {
//					Vector otherMarkers = _sampleMap.getOtherMarkers1();
//					Marker currentMarker = _sampleMap.CurrentMarker1();
//					
//					_mapField.setMarkers(otherMarkers, currentMarker);
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al llamar a setMarkers1(). " +  ex.getMessage());
//				}
//			}
//		} );

//		add(new MenuItem("Markers Mundo", idx++, 0) {
//			public void run() {
//				try {
//					Vector otherMarkers = _sampleMap.getOtherMarkers2();
//					Marker currentMarker = _sampleMap.CurrentMarker2();
//					
//					_mapField.setMarkers(otherMarkers, currentMarker);
//				}
//				catch(Exception ex){
//					System.out.println("ERROR al llamar a setMarkers2(). " +  ex.getMessage());
//				}
//			}
//
//		} );

		
	}

	private void showSplitScreen() {
		SplitScreen scr = new SplitScreen(this); 
		UiApplication.getUiApplication().pushScreen(scr);
	}

	private void showAmountZoomScreen(int mode){
		int currentZoom = _mapField.getCurrentZoom();
		AmountZoomScreen scr = new AmountZoomScreen(mode, currentZoom, this); 
		UiApplication.getUiApplication().pushScreen(scr);
	}

	public void onZoomChanged(final int zoom) {
		_mapField.setZoom(zoom);
	}


	public void onSplitChanged(int xSplit, int ySplit) {
		_mapField.setSplit(xSplit, ySplit);
	}
}
