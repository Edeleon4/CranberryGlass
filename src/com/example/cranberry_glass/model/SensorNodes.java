package com.example.cranberry_glass.model;

import java.util.ArrayList;

import android.location.Location;
import android.util.Log;

public class SensorNodes {

    private static final String TAG = SensorNodes.class.getSimpleName();
    private ArrayList<Node> nodes;
    private int currentNodeIndex;
    private int currentSensorIndex;

    public SensorNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
        this.currentNodeIndex = 0;
        this.currentSensorIndex = 0;
    }

    public ArrayList<Node> getNearbyNodes(double latitude, double longitude) {
        return null;
    }

    public String getCurrentNodeId() {
        return getCurrentNode().getNodeName();
    }

    public String getCurrentSensorType() {
        return getCurrentSensor().getName();
    }

    public String getCurrentUnits() {
        return getCurrentSensor().getUnits();
    }

    public float[] getCurrentChartData() {
        Log.i(TAG, getCurrentNode().toString());

        if (nodes.isEmpty()) {
            return new float[] { 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f };
        }
        return toChartData(getCurrentSensor().getDataArray());
    }

    private float[] toChartData(ArrayList<Float> data) {
        float[] floatArray = new float[data.size()];
        int i = 0;

        for (Float f : data) {
            floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever
                                                           // default you want.
        }
        return floatArray;

    }

    private Node getCurrentNode() {
        return nodes.get(currentNodeIndex);
    }

    private Sensor getCurrentSensor() {

        return getCurrentNode().getSensorsArray().get(currentSensorIndex);
    }

    public void shiftNodeLeft() {
        currentNodeIndex = (currentNodeIndex + nodes.size() - 1) % nodes.size();
        currentSensorIndex = 0;

    }

    public void shiftNodeRight() {
        currentNodeIndex = (currentNodeIndex + 1) % nodes.size();
        currentSensorIndex = 0;

    }

    public void shiftSensorLeft() {

        int sensorArraySize = getCurrentNode().getSensorsArray().size();
        currentSensorIndex = (currentSensorIndex - 1) > 0 ? (currentSensorIndex - 1)
                % sensorArraySize
                : 0;
    }

    public void shiftSensorRight() {

        currentSensorIndex = (currentSensorIndex + 1)
                % getCurrentNode().getSensorsArray().size();
    }

    public void setUserLocation(Location location) {
        // TODO Auto-generated method stub

    }

    public void setZerothSensor() {
        currentSensorIndex = 0;
        currentNodeIndex = 0;
    }

}
