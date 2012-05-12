
package com.mobilenik.socialBicing.ui.screens;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.mobilenik.core.common.Functions;
import com.mobilenik.core.logic.events.MKEvent;
import com.mobilenik.core.ui.Heading;
import com.mobilenik.core.ui.screens.MKScreenInfo;
import com.mobilenik.socialBicing.common.Constants;
import com.mobilenik.socialBicing.common.biz.AbsUsuario;
import com.mobilenik.socialBicing.common.biz.Bici;
import com.mobilenik.socialBicing.common.biz.EstadoBici;
import com.mobilenik.socialBicing.common.biz.Reserva;
import com.mobilenik.socialBicing.common.biz.UsuarioFinal;
import com.mobilenik.socialBicing.common.biz.UsuarioPuesto;
import com.mobilenik.socialBicing.common.geocoder.GoogleGeocoder;
import com.mobilenik.socialBicing.common.geocoder.GooglePlaceMark;
import com.mobilenik.socialBicing.logic.MKApplicationControllerE;
import com.mobilenik.socialBicing.logic.MKEventTypesE;
import com.mobilenik.socialBicing.logic.MKSessionE;
import com.mobilenik.socialBicing.ui.fields.ImageButtonField;
import com.mobilenik.util.location.Position;
import com.mobilenik.util.ui.fields.CommandButton;
import com.mobilenik.util.ui.map2.business.Marker;
import com.mobilenik.util.ui.map2.fields.MapField;
import com.mobilenik.util.ui.map2.fields.MapField.IMapEventListener;

public class BikeSearchScreen extends MKScreenE implements IListenerRequest, IMapEventListener
{

	private MapField _mapField;
	private Vector bicisLibres;
	private SampleMap sampleMap;
	private Hashtable markerBici;

	private Bici bici;
	
	public BikeSearchScreen(Vector bicisLibres) {
		super();
		this.bicisLibres = bicisLibres;
		sampleMap = new SampleMap(this);
		markerBici = new Hashtable();
		
		AbsUsuario usuario = MKSessionE.getInstance().usuario;
		
		
		if(usuario instanceof UsuarioFinal){
			UsuarioFinal usuarioFinal = (UsuarioFinal)usuario;
			bici = usuarioFinal.getBici();
		}
		else if(usuario instanceof UsuarioPuesto){
			UsuarioPuesto usuarioPuesto = (UsuarioPuesto)usuario;
			Vector bicis = usuarioPuesto.getBicis();
			if(bicis.size()>0){
				bici = (Bici)bicis.elementAt(0);
			}
		}

//		///TEST
//		usuario.setEstadoUsuario(EstadoUsuario.STATE_BIKE_RESERVED);
//		bici.setEstado(EstadoBici.STATE_RESERVED);
//		bici.setReserva(new Reserva(bici.getId(), 1000, "1234"));
//		bici.setResponsable(12345/*usuario.getId()*/);
//		///
		
		drawScreen();
	}


	public class ManagerPosicion extends VerticalFieldManager{

		private Bitmap searchButtonFocus;
		private Bitmap searchButtonUnfocus;
		private GooglePlaceMark selectedPlace;
		
		
//		ObjectChoiceField ocfPosition;
		EditField efPosition;
		
		
		public ManagerPosicion(){
			
			super();
			
			searchButtonFocus = Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "buscar-press.png");
			searchButtonUnfocus = Functions.getImageFullPath(MKScreenInfo.getInstance().getImgDir() + "buscar-ective.png");

			draw();
			

		}
		private void draw(){
			efPosition = new EditField("Ubicación: ", "");
			add(efPosition);
			
//			int iSetTo = 2;
//			String choices[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
//			ocfPosition = new ObjectChoiceField("Ubicación",choices,iSetTo);
//			add(ocfPosition);

//			CommandButton cbSearch = new CommandButton("Buscar"){
//				protected void onClick() {
//				}
//			};
			
			ImageButtonField btnSearch = new ImageButtonField(null, Color.BLACK, 
					searchButtonFocus, 
					searchButtonUnfocus){
				protected void onClick() {
//					int index = ocfPosition.getSelectedIndex();
//					String address = (String)ocfPosition.getChoice(index);; 
					
					String address = efPosition.getText();
					GoogleGeocoder geoCoder = new GoogleGeocoder();
				    Vector resultados = geoCoder.getGeoAddressPlaces(address);
			    	int size = resultados.size();
				    
				    if(size>1){
//				    	
				    	
				    }
				    else if(size==1){
				    	selectedPlace = (GooglePlaceMark)resultados.elementAt(0);
				    	efPosition.setText(selectedPlace.getName());
				    }
				    else if(size==0){
				    	Dialog.alert("No se pudo encontrar la dirección");
				    }
				}
			};
			
			add(btnSearch);
			
//			_mapField.refresh();
		}

	}



	protected void drawScreen() {


		if(this.vfm != null){
			this.vfm.deleteAll();
		}

		add(new Heading(getResizeableImgDir() + Constants.IMG_HEADING_1, true));

		vfm.add(new LabelField());

//		ManagerPosicion mgrPosition = new ManagerPosicion();
//		vfm.add(mgrPosition);

//		vfm.add(new LabelField("lalala", Field.FOCUSABLE));
		vfm.add(new LabelField(""));
		vfm.add(new SeparatorField(Field.USE_ALL_WIDTH));


		System.out.println("MapScreen1. " + toString());

		Marker currentMarker = getCurrentMarkerAqui();
		Vector otherMarkers = getOtherMarkers();
		_mapField = sampleMap.getMap(currentMarker, otherMarkers);

		System.out.println("MapScreen2");
		vfm.add(_mapField);
		vfm.add(new SeparatorField(Field.USE_ALL_WIDTH));
		vfm.add(new LabelField(""));
		
		final AbsUsuario usuario = MKSessionE.getInstance().usuario;
//		HorizontalFieldManager hfmEstado = new HorizontalFieldManager();


		
//		Tenes la bici 1234 (responsabe, y la bici esta libre)
//		Estás usando la bici 1234 (responsable, y en estado en uso)
//		Te reservaron la bici 1234 (responsable y la bici está reservada)
//		Tenes reservada la bici 1234 por 1'30"más
		
		
		String strEstado = "";
		
		if(bici!=null){
			if(usuario.getId() == bici.getResponsable()){
				if(bici.getEstado() == EstadoBici.STATE_FREE){
					strEstado = "Tenés la bicicleta " + bici.getCode(); 
				}
				else if(bici.getEstado() == EstadoBici.STATE_IN_USE){
					strEstado = "Estás usando la bicicleta " + bici.getCode(); 
				}
				else if(bici.getEstado() == EstadoBici.STATE_RESERVED){
					strEstado = "Te reservaron la bicicleta " + bici.getCode(); 
				}
			}
		}
		else{
			Reserva reserva = MKSessionE.getInstance().userStatus.getReserve();
			if(reserva!=null){
				long time = reserva.getExpirationTime();
				if(time>0){
					strEstado = "Tenés reservada la bici " + reserva.getBike().getCode() + " por " + com.mobilenik.socialBicing.common.Functions.timeStamToString(System.currentTimeMillis(), reserva.getExpirationTime()) + " más";
				}
				else{
					strEstado = "Expiró tu reservada de la bici " + bici.getCode();
				}
			}
			else{
				strEstado = "No tenés una bicicleta asignada"; 
			}
		}
		
		
		LabelField lfEstado = new LabelField(strEstado);
		vfm.add(lfEstado);
		
		vfm.add(new LabelField());


		if(bici!=null){
			CommandButton bfSiguiente = new CommandButton("Ver bicicleta", Field.FIELD_HCENTER){
				public void onClick(){
					dispatchMkEvent (new MKEvent(MKEventTypesE.GO_BICI_SCREEN, bici));
				}
			};
			vfm.add(bfSiguiente);
		}
		else{
			Reserva reserva = MKSessionE.getInstance().userStatus.getReserve();
			if(reserva!=null){
				final Bici reservedBike = reserva.getBike();
				CommandButton bfSiguiente = new CommandButton("Ver bicicleta", Field.FIELD_HCENTER){
					public void onClick(){
						dispatchMkEvent (new MKEvent(MKEventTypesE.GO_BICI_SCREEN, reservedBike));
					}
				};
				vfm.add(bfSiguiente);
				
			}
		}
		
		add(vfm);
	}


	private Marker getCurrentMarkerAqui() {

		Position pos = MKApplicationControllerE.getInstance().getBestLocation();


		Marker currentMarker = new Marker();
		
		currentMarker.setName("Usted está aquí");
		if(bici!=null){
			currentMarker.setDescription1(EstadoBici.toString(bici.getEstado()));
			markerBici.put(currentMarker.getName(), bici);
		}
		else{
			currentMarker.setDescription1("No tenés una bicicleta asignada");
		}
		currentMarker.setDescription2(" ");
		
		
		currentMarker.setLatitude(pos.getLatitude());
		currentMarker.setLongitude(pos.getLongitude());
		
		return currentMarker;
	}

	private Vector getOtherMarkers(){

		Vector places = new Vector();

		if(bicisLibres==null){
//			MKApplicationControllerE.getInstance().mkEventHandler(new MKEvent(MKEventTypesE.GET_FREE_BIKES_REQUEST, null));
		}
		else{
			int size = bicisLibres.size();
			for (int i = 0; i < size; i++) {
				Bici bici = (Bici)bicisLibres.elementAt(i);
				com.mobilenik.socialBicing.common.biz.LatLng latLng = bici.getLatLng();
				Marker marker = new Marker();
				marker.setName(bici.getCalle());
				marker.setDescription1(EstadoBici.toString(bici.getEstado()));
				marker.setDescription2(" ");
				marker.setLatitude(latLng.getLat());
				marker.setLongitude(latLng.getLng());
				places.addElement(marker);
				markerBici.put(marker.getName(), bici);
			}
		}
		return places;
	}


	public void onResult(Object obj) {
		bicisLibres = (Vector)obj;

	}

	public void onMarkerSelected(Marker marker) {
		System.out.println("");
		Bici bici = (Bici)markerBici.get(marker.getName());
		dispatchMkEvent (new MKEvent(MKEventTypesE.GO_BICI_SCREEN, bici));
	}	
}
