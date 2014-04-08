package com.example.cranberry_glass.model;

import java.util.ArrayList;

public class Node {
	String nodeName;
	ArrayList<Sensor> sensorsArray;

	public Node(String nodeName, ArrayList<Sensor> sensorArray) {
		this.nodeName = nodeName;
		this.sensorsArray = sensorArray;
	}




	@Override
	public String toString() {
		return "Node [nodeName=" + nodeName + ", sensorsArray=" + sensorsArray
				+ "]";
	}




	public String getNodeName() {
		return nodeName;
	}

	public ArrayList<Sensor> getSensorsArray() {
		return sensorsArray;
	}

}
