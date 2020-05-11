package com.serverless.covid19.model;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serverless.covid19.helper.Covid19DateHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CovidData {
	
	private String provinceState;
	private String countryRegion;
	private String lastUpdate;
	private int confirmed;
	private int deaths;
	private int recovered;
	

	public String getProvinceState() {
		return provinceState;
	}
	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getRecovered() {
		return recovered;
	}
	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}
	
	public boolean compareDate(CovidData data) throws ParseException {
		
		Date lastUpdate = Covid19DateHelper.parse(this.getLastUpdate());
		Date lastUpdateIncoming = Covid19DateHelper.parse(data.getLastUpdate());
		
		return lastUpdate.equals(lastUpdateIncoming);
		
	}
	@Override
	public String toString() {
		return "CovidData [provinceState=" + provinceState + ", countryRegion=" + countryRegion + ", lastUpdate="
				+ lastUpdate + ", confirmed=" + confirmed + ", deaths=" + deaths + ", recovered=" + recovered + "]";
	}
	
	

}
