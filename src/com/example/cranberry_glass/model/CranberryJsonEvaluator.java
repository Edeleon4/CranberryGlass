package com.example.cranberry_glass.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CranberryJsonEvaluator {
	String siteURL;
	final int MAX_POINT = 10;
	final CranberryServerManager server = new CranberryServerManager();
	private static final Set<String> SENSORS = new HashSet<String>(
			Arrays.asList(new String[] { "bmp_temperature", "bmp_pressure",
					"illuminance", "sht_humidity", "sht_temperature",
					"solar_voltage" }));

	public CranberryJsonEvaluator(String url) {
		siteURL = url;
	}

	/**
	 * Given a siteURL get the URL to site's devices. Each device contains a
	 * list of sensors
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getSiteJSON() throws IOException, JSONException {
		String siteDevicesLink = "";
		try {
			JSONObject siteJson = server.getJSONFromURL(siteURL);
			JSONObject siteDevicesValues = (JSONObject) siteJson.get("devices");
			siteDevicesLink = siteDevicesValues.get("_href").toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return siteDevicesLink;
	}

	public ArrayList<Node> getListOfNodes(String siteDevicesURL) {
		ArrayList<Node> nodesList = null;
		try {
			JSONObject siteJson = server.getJSONFromURL(siteDevicesURL);
			JSONArray nodesArray = siteJson.getJSONArray("data");
			nodesList = new ArrayList<Node>();
			for (int i = 0; i < nodesArray.length(); i++) {
				JSONObject node = nodesArray.getJSONObject(i);
				nodesList.add(new Node(node.getString("name"),
						getSensorsofNode(node)));
				getSensorsofNode(node);
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
		return nodesList;

	}

	public ArrayList<Sensor> getSensorsofNode(JSONObject node)
			throws JSONException, ClientProtocolException, IOException {
		JSONArray sensorsOfNode = node.getJSONObject("sensors").getJSONArray(
				"data");
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		for (int i = 0; i < sensorsOfNode.length(); i++) {
			JSONObject sensor = sensorsOfNode.getJSONObject(i);
			if (SENSORS.contains(sensor.get("metric"))) {
				String dataURL= sensor.getJSONObject("history").getString("_href");
				JSONArray data = server.getJSONFromURL(dataURL).getJSONArray("data");
				Double[] dataArray = new Double[MAX_POINT];

				for (int j=0 ; j <MAX_POINT ; j++){
				dataArray[j] = data.getJSONObject(j).getDouble("value");
				}
				sensors.add(new Sensor(sensor.getString("metric"), sensor
						.getString("unit"), dataURL, dataArray));


				
			}

		}
		return sensors;

	}
}
