package com.serverless.covid19.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeries {
	
	List<CovidData> info;

	public List<CovidData> getInfo() {
		return info;
	}

	public void setInfo(List<CovidData> info) {
		this.info = info;
	}
	

}
