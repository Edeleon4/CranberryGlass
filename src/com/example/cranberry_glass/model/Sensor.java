package com.example.cranberry_glass.model;

import java.util.Arrays;

public class Sensor {
	
	@Override
	public String toString() {
		return "Sensor [units=" + units + ", name=" + name + ", dataUrl="
				+ dataUrl + "dataArray=" + Arrays.toString(dataArray) + "]";
	}
	private String units;
	private String name;
	private String dataUrl;
	private float recentValue;
	private ArrayList<float> dataArray;

	public Sensor(String name, String units, String dataUrl, ArrayList<float> dataArray, float recentValue){
		
		this.units = units; 
		this.name = name;
		this.dataUrl = dataUrl;
		this.recentValue = recentValue;
		this.dataArray = dataArray;
	}
	
	public String getName(){
		return name;
	}
	
	public String getUnits(){
		return units;
	}
	public String getDataUrl(){
		return dataUrl;
	}
	
	public float getRecentValue(){
		return recentValue;
	}

	public ArrayList<float> getDataArray(){
		return dataArray;
	}

}

