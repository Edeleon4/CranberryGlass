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
    public static final Set<String> SENSORS = new HashSet<String>(
            Arrays.asList(new String[] { "bmp_temperature", "bmp_pressure",
                    "illuminance", "sht_humidity", "sht_temperature",
                    "solar_voltage" }));
    private static final String TAG = CranberryJsonEvaluator.class
            .getSimpleName();

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
        String siteSummaryHref = "";
        try {
            JSONObject siteJson = server.getJSONFromURL(siteURL);
            JSONObject siteSummaryObject = siteJson.getJSONObject("_links");
            JSONObject siteSummaryObjectSub = siteSummaryObject
                    .getJSONObject("ch:siteSummary");
            siteSummaryHref = siteSummaryObjectSub.getString("href");
            // JSONObject siteDevicesValues = (JSONObject)
            // siteJson.get("devices");
            // siteSummaryHref = siteSummaryObject.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        return siteSummaryHref;
    }

    public ArrayList<Node> getListOfNodes(String siteSummaryHref) {
        ArrayList<Node> nodesList = null;
        try {
            JSONObject siteJson = server.getJSONFromURL(siteSummaryHref);
            JSONArray nodesArray = siteJson.getJSONArray("devices");
            nodesList = new ArrayList<Node>();
            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject node = nodesArray.getJSONObject(i);
                nodesList.add(new Node(node.getString("name"),
                        getSensorsofNode(node)));
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

    // node --> sensors --> data

    public ArrayList<Sensor> getSensorsofNode(JSONObject node)
            throws JSONException, ClientProtocolException, IOException {
        JSONArray sensorsOfNode = node.getJSONArray("sensors");
        ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        for (int i = 0; i < sensorsOfNode.length(); i++) {
            // Log.i(TAG,i+"");
            JSONObject sensor = sensorsOfNode.getJSONObject(i);
            JSONArray sensorData = sensor.getJSONArray("data");
            String metric = sensor.getString("metric");
            String href = sensor.getString("href");
            String unit = sensor.getString("unit");
            float recentValue = (float) sensor.getDouble("value");

            ArrayList<Float> dataArray = new ArrayList<Float>();
            // ArrayList<String> dataArray = new ArrayList<double>();

            for (int j = 0; j < sensorData.length(); j++) {
                dataArray.add((float) sensorData.getJSONObject(j).getDouble(
                        "value"));
            }
            sensors.add(new Sensor(metric, unit, href, dataArray, recentValue));
        }
        return sensors;

    }
}
