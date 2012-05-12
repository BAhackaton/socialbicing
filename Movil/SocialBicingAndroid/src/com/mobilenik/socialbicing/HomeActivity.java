package com.mobilenik.socialbicing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mobilenik.socialbicing.entities.Bike;

public class HomeActivity extends MapActivity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String METHOD_NAME = "GetBikes";
	private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
	private static final String URL = "http://ba.mobilenik.com:81/socialbicing/socialbicing.asmx";

	private static final double LON_CABA_CENTRO = -58.44574;
	private static final double LAT_CABA_CENTRO = -34.60753;
	private static final String TAG = "HomeActivity";

	private ArrayList<Bike> bikes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		GeoPoint point = new GeoPoint((int) (LAT_CABA_CENTRO * 1E6),
				(int) (LON_CABA_CENTRO * 1E6));

		MapController controller = mapView.getController();
		controller.setZoom(12);
		controller.animateTo(point);

		List<Overlay> mapOverlays = mapView.getOverlays();

		Drawable drawable;
		
		// TODO: load stations.

		// InputStream inputStream = getResources().openRawResource(
		// R.raw.estaciones_bicis);
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// inputStream));
		//
		//		drawable = this.getResources().getDrawable(R.drawable.marker);
//		StationsItemizedOverlay stationsOverlay = new StationsItemizedOverlay(
//				drawable, this);
//
//		try {
//			// Skip first line.
//			String line = reader.readLine();
//			while ((line = reader.readLine()) != null) {
//				String[] values = line.split(",");
//				Log.d(TAG, "lat: " + values[values.length - 2] + ", lon: "
//						+ values[values.length - 1]);
//				int lat = (int) (Double.parseDouble(values[values.length - 2]) * 1E6);
//				int lon = (int) (Double.parseDouble(values[values.length - 1]) * 1E6);
//				GeoPoint gp = new GeoPoint(lat, lon);
//				OverlayItem overlayitem = new OverlayItem(gp, "Hola, CABA!",
//						"Bienvenidos a CIMO!");
//				stationsOverlay.addOverlay(overlayitem);
//			}
//		} catch (IOException e) {
//			Log.d(TAG, "Error loading stations.");
//		}
//
//		mapOverlays.add(stationsOverlay);

		Random r = new Random();
		bikes = new ArrayList<Bike>();

		for (int i = 0; i < 4; i++) {
			bikes.add(new Bike(
					(int) (LAT_CABA_CENTRO * 1E6 - 50000 + 100000 * r
							.nextFloat()),
					(int) (LON_CABA_CENTRO * 1E6 - 50000 + 100000 * r
							.nextFloat())));
		}

		drawable = this.getResources().getDrawable(R.drawable.cycling_green);
		StationsItemizedOverlay bikesOverlay = new StationsItemizedOverlay(
				drawable, this);
		for (Bike bike : bikes) {
			GeoPoint gp = new GeoPoint(bike.getLatitude(), bike.getLongitude());
			OverlayItem overlayitem = new OverlayItem(gp, "Hola, CABA!",
					"Bienvenidos a CIMO!");
			bikesOverlay.addOverlay(overlayitem);
		}

		mapOverlays.add(bikesOverlay);

		// Call web service.
		// SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		//
		// SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
		// SoapEnvelope.VER11);
		// envelope.dotNet = true;
		// envelope.setOutputSoapObject(Request);
		//
		// HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		// try {
		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// SoapObject response = (SoapObject) envelope.getResponse();
		// int result = Integer.parseInt(response.getProperty(0).toString());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
