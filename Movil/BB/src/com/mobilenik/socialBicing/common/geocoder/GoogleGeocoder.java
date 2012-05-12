package com.mobilenik.socialBicing.common.geocoder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.mobilenik.util.location.geocoder.CellTowerGeocodingRequest;
import com.mobilenik.util.location.geocoder.CellTowerGeocodingResult;
import com.mobilenik.util.location.geocoder.ICellTowerGeocoder;
import com.mobilenik.util.ws.KXml.KXmlParser;

public class GoogleGeocoder implements ICellTowerGeocoder {

	
	public static final String GEO_ADDRESS_URI = "http://maps.google.com/maps/geo?q=";//URL Query
	public static final String GEO_ADDRESS_URI_PARAMS = "&output=xml&oe=utf8&sensor=true&hl=es&gl=ar";//Parametros Generales

	public CellTowerGeocodingResult geocode(CellTowerGeocodingRequest info) throws Exception {

		CellTowerGeocodingResult result = null;
		HttpConnection http = null;
		OutputStream os = null;
		InputStream is = null;
		DataOutputStream dos = null;

		try{
			String googleURI = "http://www.google.com/glm/mmap";

			// Create the connection
			http = (HttpConnection) Connector.open(googleURI);

			http.setRequestMethod(HttpConnection.POST);

			byte[] formData = getFormPostData(info.cellId, info.mcc, info.mnc, info.lac);

			os = http.openOutputStream();

			os.write(formData, 0, formData.length);

			//			dos = new DataOutputStream(os); 
			//			dos.writeShort(21); 
			//			dos.writeLong(0); 
			//			dos.writeUTF("fr"); 
			//			dos.writeUTF("Sony_Ericsson-K750"); 
			//			dos.writeUTF("1.3.1"); 
			//			dos.writeUTF("Web"); 
			//			dos.writeByte(27); 
			//
			//			dos.writeInt(0); 
			//			dos.writeInt(0);
			//			dos.writeInt(3); 
			//			dos.writeUTF("");
			//			dos.writeInt(info.cellId); // CELL-ID 
			//			dos.writeInt(info.lac); // LAC 
			//			dos.writeInt(0); 
			//			dos.writeInt(0); 
			//			dos.writeInt(0);
			//			dos.writeInt(0); 


			//This line sends the request
			int rc = http.getResponseCode();
			if (rc != HttpConnection.HTTP_OK)
			{
				return CellTowerGeocodingResult.EMPTY;
			}            
			is = http.openInputStream();
			DataInputStream dis = new DataInputStream(is); 

			// Read some prior data 
			dis.readShort(); 
			dis.readByte(); 
			// Read the error-code 
			int errorCode = dis.readInt(); 

			double lat = 0.0;
			double lng = 0.0;

			if (errorCode == 0)
			{ 
				lat = (double) dis.readInt() / 1000000D; 
				lng = (double) dis.readInt() / 1000000D; 
			}
			else{
				return CellTowerGeocodingResult.EMPTY;
			}

			result = new CellTowerGeocodingResult();
			result.setLat(lat);
			result.setLng(lng);
			return result;
		}
		catch(Exception e){
			return CellTowerGeocodingResult.EMPTY;
		}
		finally
		{
			try
			{
				if (is != null){
					is.close();
					is = null;
				}
				if (os != null){
					os.close();
					os = null;
				}
				if (dos != null){
					dos.close();
					dos = null;
				}
				if (http != null){
					http.close();
					http = null;
				}
			}
			catch(Exception ex){}
		}
	}

	/**
	 * Obtiene un Vector de lugares que coinciden con la busqueda de la dirección dada.
	 * @param address Dirección para búsqueda
	 * @return Vector de GooglePlaceMarks
	 */
	public Vector getGeoAddressPlaces(String address){
		Vector direcciones = null;
		
		HttpConnection http = null;
		InputStream is = null;
		InputStreamReader isr = null;
		KXmlParser parser = null;

		try{
			//Encoding de la URL
			String googleSearchURI = GEO_ADDRESS_URI+URLEncoder.encode(address, "UTF-8")+GEO_ADDRESS_URI_PARAMS + ";deviceside=true";
			
			//Conexion
			http = (HttpConnection) Connector.open(googleSearchURI);
			http.setRequestMethod(HttpConnection.POST);
			
			//This line sends the request
			int rc = http.getResponseCode();
			if (rc != HttpConnection.HTTP_OK)
			{
				return null;
			}            
			is = http.openInputStream();
			
			//Armo el Parser
			parser = new KXmlParser();
			isr = new InputStreamReader(is, "UTF-8");
			parser.setInput(isr);
			GeoAddressesParser adressesParser = new GeoAddressesParser();
			// Parseo y devuelvo los resultados
			direcciones = adressesParser.parseBody(parser);
			return direcciones;
		}catch(Exception e){
			
		}
		finally{
			try
			{
				if (is != null){
					is.close();
					is = null;
				}
				if(isr != null){
					isr.close();
					isr = null;
				}
				if(parser != null){
					parser.setInput(null);
					parser = null;
				}
				if (http != null){
					http.close();
					http = null;
				}
				System.gc();
			}
			catch(Exception ex){
			}
		}
		
		return direcciones;
	}
	
	

	private static byte[] getFormPostData(int cellTowerId, int mobileCountryCode, int mobileNetworkCode, int locationAreaCode) //, bool shortCID
	{
		byte[] pd = new byte[55];
		pd[1] = 14;     //0x0e;
		pd[16] = 27;    //0x1b;
		pd[47] = (byte)255;   //0xff;
		pd[48] = (byte)255;   //0xff;
		pd[49] = (byte)255;   //0xff;
		pd[50] = (byte)255;   //0xff;

		pd[28] = (cellTowerId > 65536) ? (byte)5 : (byte)3; // GSM uses 4 digits while UTMS used 6 digits (hex)

		shift(pd, 17, mobileNetworkCode);
		shift(pd, 21, mobileCountryCode);
		shift(pd, 31, cellTowerId);
		shift(pd, 35, locationAreaCode);
		shift(pd, 39, mobileNetworkCode);
		shift(pd, 43, mobileCountryCode);

		return pd;
	}

	private static void shift(byte[] data, int startIndex, int leftOperand)
	{
		int rightOperand = 24;

		for (int i = 0; i < 4; i++, rightOperand -= 8)
		{
			data[startIndex++] = (byte)((leftOperand >> rightOperand) & 255);
		}
	}

	//	private static void getFormPostData(CellTowerGeocodingRequest ct, OutputStream outputStream) throws IOException
	//	{
	//		DataOutputStream os = new DataOutputStream(outputStream); 
	//		os.writeShort(21); 
	//		os.writeLong(0); 
	//		os.writeUTF("fr"); 
	//		os.writeUTF("Sony_Ericsson-K750"); 
	//		os.writeUTF("1.3.1"); 
	//		os.writeUTF("Web"); 
	//		os.writeByte(27); 
	//
	//		os.writeInt(0); os.writeInt(0); os.writeInt(3); 
	//		os.writeUTF(""); 
	//		os.writeInt(ct.cellId); // CELL-ID 
	//		os.writeInt(ct.lac); // LAC 
	//		os.writeInt(0); os.writeInt(0); 
	//		os.writeInt(0); os.writeInt(0); 
	//		//os.flush();
	//		
	//		
	//	}
}
