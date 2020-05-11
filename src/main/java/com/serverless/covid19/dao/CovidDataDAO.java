package com.serverless.covid19.dao;

import com.serverless.covid19.model.CovidData;

public class CovidDataDAO {

	private String lastUpdate;
	private int confirmed;
	private int deaths;
	private int recovered;
	
	public CovidDataDAO() {
		
	}
	public CovidDataDAO(CovidData data) {
		this.lastUpdate = data.getLastUpdate();
		this.confirmed = data.getConfirmed();
		this.recovered = data.getRecovered();
		this.deaths = data.getDeaths();
	}
	
	public void aggregate(CovidData data) {		
		this.confirmed+=data.getConfirmed();
		this.recovered+=data.getRecovered();
		this.deaths+=data.getDeaths();		
	}
	public void aggregate(CovidDataDAO data) {	
		this.lastUpdate = data.getLastUpdate();
		this.confirmed+=data.getConfirmed();
		this.recovered+=data.getRecovered();
		this.deaths+=data.getDeaths();		
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
	
	
}
