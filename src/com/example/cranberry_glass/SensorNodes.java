package com.example.cranberry_glass;

import java.util.Arrays;
import java.util.List;


public class SensorNodes {

    public SensorNodes(GraphService graphService) {
        // TODO Auto-generated constructor stub
    }

    public List<SensorNode> getNearbyNodes(double latitude, double longitude) {
        return Arrays.asList(new SensorNode(new float[] { 10, 12, 7, 14, 15, 19, 13, 2, 10, 13, 13, 10, 15, 14 }));
    }

}
