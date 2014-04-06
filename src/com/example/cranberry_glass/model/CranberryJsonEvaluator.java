package com.example.cranberry_glass.model;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
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
			System.out.println("$$$$$$@#@#@$$");
			System.out.println(siteJson);
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
