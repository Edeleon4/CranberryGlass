package com.example.cranberry_glass.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SensorNodes {

    private ArrayList<Node> nodes;
    private int currentNodeIndex;
    private int currentSensorIndex;

    public SensorNodes(ArrayList<Node> nodes) {
        checkRep();
        this.nodes = nodes;
        this.currentNodeIndex = 0;
        this.currentSensorIndex = 0;
    }


    private void checkRep() {
        assert(!nodes.isEmpty());
        assert(currentNodeIndex < nodes.size());
        assert(currentSensorIndex < getCurrentNode().getSensorsArray().size());
        assert(currentNodeIndex >= 0);
        assert(currentSensorIndex >= 0);
    }

    public ArrayList<Node> getNearbyNodes(double latitude, double longitude) {
        return null;
    }
    public float[] getCurrentData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        return getCurrentSensor().getDataArray();
    }
    
    
    
    private Node getCurrentNode(){
        checkRep();
        return nodes.get(currentNodeIndex);
    }
    private Sensor getCurrentSensor(){
        checkRep();
        return getCurrentNode().getSensorsArray().get(currentSensorIndex);
    }
    private Node getNextNodeRight(){
        currentNodeIndex = (currentNodeIndex+1)%nodes.size();
        return getCurrentNode();
    }
    private Sensor getNextSensorRight(){
        currentSensorIndex = (currentSensorIndex+1)%getCurrentNode().getSensorsArray().size();
        return getCurrentSensor();
    }
    private Node getNextNodeLeft(){
        currentNodeIndex = (currentNodeIndex+nodes.size()-1)%nodes.size();
        return getCurrentNode();
    }
    private Sensor getNextSensorLeft(){
        int sensorArraySize = getCurrentNode().getSensorsArray().size();
        currentSensorIndex = (currentSensorIndex+sensorArraySize-1)%sensorArraySize;
        return getCurrentSensor();
    }

}
