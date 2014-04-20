package com.example.cranberry_glass.model;

import java.util.ArrayList;
/*
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
    public String getCurrentNodeId(){
        return getCurrentSensor().getName();
    }
    public String getCurrentSensorType(){
        return getCurrentSensor().getName();
    }
    public String getCurrentUnits(){
        return getCurrentSensor().getUnits();
    }
    public float[] getCurrentData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        return getCurrentSensor().getDataArray();
    }
    public float[] getNodeShiftRightData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        this.shiftNodeRight();
        return getCurrentData();
    }
    public float[] getNodeShiftLeftData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        this.shiftNodeLeft();
        return getCurrentData();
    }
    public float[] getSensorShiftRightData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        this.shiftSensorRight();
        return getCurrentData();
    }
    public float[] getSensorShiftLeftData(){
        if(nodes.isEmpty()){
            return new float[]{1,2,3,4,5,6,7,8,9,10};
        }
        checkRep();
        this.shiftSensorLeft();
        return getCurrentData();
    }
    
    
    private Node getCurrentNode(){
        checkRep();
        return nodes.get(currentNodeIndex);
    }
    private Sensor getCurrentSensor(){
        checkRep();
        return getCurrentNode().getSensorsArray().get(currentSensorIndex);
    }
    
    private void shiftNodeLeft() {
        currentNodeIndex = (currentNodeIndex+nodes.size()-1)%nodes.size();
    }


    private void shiftNodeRight() {
        currentNodeIndex = (currentNodeIndex+1)%nodes.size();
    }
    private void shiftSensorLeft() {
        int sensorArraySize = getCurrentNode().getSensorsArray().size();
        currentSensorIndex = (currentSensorIndex+sensorArraySize-1)%sensorArraySize;
    }


    private void shiftSensorRight() {
        currentSensorIndex = (currentSensorIndex+1)%getCurrentNode().getSensorsArray().size();
    }


}*/
