package com.example.cranberry_glass.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Sensor {
	
	@Override
	public String toString() {
		return "Sensor [units=" + units + ", name=" + name + ", dataUrl="
				+ dataUrl + "dataArray=" + dataArray.toString() + "]";
	}
	private String units;
	private String name;
	private String dataUrl;
	private float recentValue;
	private ArrayList<Float> dataArray;

	public Sensor(String name, String units, String dataUrl, ArrayList<Float> dataArray, float recentValue){
		
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

	public ArrayList<Float> getDataArray(){
		return dataArray;
	}

}

