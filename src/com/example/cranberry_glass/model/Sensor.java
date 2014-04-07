package com.example.cranberry_glass.model;

public class Sensor {
	
	private String units;
	private String name;
	private String dataUrl;

	public Sensor(String name, String units, String dataUrl){
		
		this.units = units; 
		this.name = name;
		this.dataUrl = dataUrl;
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
}

