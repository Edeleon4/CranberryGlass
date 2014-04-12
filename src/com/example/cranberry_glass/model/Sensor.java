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
	private float[] dataArray;

	public Sensor(String name, String units, String dataUrl, double[] dataArray2){
		
		this.units = units; 
		this.name = name;
		this.dataUrl = dataUrl;
		this.dataArray = convertDoubleArrayToFloatArray(dataArray2);
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

	public float[] getDataArray(){
		return dataArray;
	}
	private float[] convertDoubleArrayToFloatArray(double[] doubles){
	    float[] floatArray = new float[doubles.length];
	    for (int i = 0 ; i < doubles.length; i++)
	    {
	        floatArray[i] = (float) doubles[i];
	    }
	    return floatArray;
	}

}

