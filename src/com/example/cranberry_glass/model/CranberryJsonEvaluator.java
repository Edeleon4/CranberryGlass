package com.example.cranberry_glass.model;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CranberryJsonEvaluator {
	String siteURL;
	final CranberryServerManager server = new CranberryServerManager();


	public CranberryJsonEvaluator(String url) {
		siteURL = url;
	}
	/**
	 * Given a siteURL get the URL to site's devices. Each device contains a list of sensors
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getSiteJSON() throws IOException, JSONException {
		try {
			JSONObject siteJson = server.getJSONFromURL(siteURL);
    		JSONObject siteDevicesValues = (JSONObject) siteJson.get("devices");
    		String siteDevicesLink = siteDevicesValues.get("_href").toString();
			return siteDevicesLink;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void getListOfNodes(String siteDevicesURL){
		try {
			JSONObject siteJson = server.getJSONFromURL(siteDevicesURL);
			JSONArray sensorsArray = siteJson.getJSONArray("data");
			
			System.out.println("$$$$$$@#@#@$$");
			for ( int i = 0 ; i < sensorsArray.length() ; i++ ){
				System.out.println(i);
				JSONArray blah = sensorsArray.getJSONObject(i).getJSONArray("data");
				System.out.println(blah.get("sensors"));
				break;
				
				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
