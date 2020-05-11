package com.serverless.covid19.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.serverless.covid19.helper.Covid19DateHelper;
import com.serverless.covid19.model.CovidData;



public class TimeSeriesDAO {


	//country and data
	Map<String,List<CovidDataDAO>> countryTs = new HashMap<>();
	ArrayList<String> eu = new ArrayList<String>(Arrays.asList("Germany", "UK", "Spain", "Italy","France","Germany","Ukraine","Poland","Romania","Belgium","Netherlands","Czechia","Greece","Portgual","Sweden","Hungary","Belarus","Austria","Serbia","Switzerland","Bulgaria","Denmark","Finland","Slovakia",
			"Norway","Ireland","Croatia","Moldova","Bosnia and Herzegovina","Albania","Lithuania","North Macedonia","Slovenia","Latvia","Estonia","Montenegro","Malta","Luxembourg","Iceland","Andorra","Monaco","Liechtenstein","San Marino","Holy See"));
	
	public TimeSeriesDAO(TimeSeriesDAO dao){
		this.countryTs = dao.getAllCountries();
	}
	public TimeSeriesDAO(){
		
	}
	public void add(CovidData data) {
		String key = data.getCountryRegion();
		List<CovidDataDAO> d = countryTs.get(key);
		
		if(d!=null) {
			CovidDataDAO dao = new CovidDataDAO(data);
			d.add(dao);
		} else {
			d = new ArrayList<CovidDataDAO>();
			CovidDataDAO dao = new CovidDataDAO(data);
			d.add(dao);
			countryTs.put(key, d);
		}
	}
	
	private boolean hasCoreCountries(String key) {
		ArrayList<String> countries = new ArrayList<String>(Arrays.asList("US", "Spain", "Italy","Iran","France","Germany","Mainland China" ));
		if (countries.contains(key)) {
			return true;
		}
		return false;
	}
	private boolean isEurope(String key) {
		ArrayList<String> countries = new ArrayList<String>(Arrays.asList("US", "Spain", "Italy","Iran","France","Germany"));
		if (countries.contains(key)) {
			return true;
		}
		return false;
	}
	
	final static ArrayList<String> euCountries = new ArrayList<String>(Arrays.asList("US", "Spain", "Italy","France","Germany"));
	
	private ArrayList<String> europeanCountries() {
		return euCountries;
	}
	
	public void squash(CovidData data, String processingDate) throws ParseException {
		
		String key = data.getCountryRegion();
		List<CovidDataDAO> d = countryTs.get(key);
		
		if(hasCoreCountries(key)==false) {
			return;
		}
		
		if(d!=null) {			
			CovidDataDAO temp = d.get(d.size()-1);
			Date lastUpdate = Covid19DateHelper.parse(temp.getLastUpdate());
			Date lastUpdateIncoming = Covid19DateHelper.parse(processingDate);
			
			if(lastUpdateIncoming.before(lastUpdate) || lastUpdateIncoming.equals(lastUpdate) ) {				
				temp.aggregate(data);
			} else  {
				temp = new CovidDataDAO(data);
				d.add(temp);
			}
		} else {
			d = new ArrayList<CovidDataDAO>();
			CovidDataDAO dao = new CovidDataDAO(data);
			d.add(dao);
			countryTs.put(key, d);
		}
	}
	
	private ArrayList<String> getEuropean(){
		return euCountries;
	}
	
	public Map<String,List<CovidDataDAO>> getAllCountries() {
		return countryTs;
	}

	
	public ArrayList<String> getEu() {
		return eu;
	}
	public void setEu(ArrayList<String> eu) {
		this.eu = eu;
	}
}
