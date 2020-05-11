package com.serverless.covid19.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.serverless.covid19.dao.CovidDataDAO;
import com.serverless.covid19.dao.TimeSeriesDAO;
import com.serverless.covid19.model.CovidData;


public class TimeSeriesService {

	TreeMap<String,CovidDataDAO> dateHolder = new TreeMap<>();
	
	public TimeSeriesDAO process(TimeSeriesDAO exisiting, CovidData[] series, String date) throws ParseException {
		if (exisiting == null) {
			exisiting = new TimeSeriesDAO();
		}
		TimeSeriesDAO ts = new TimeSeriesDAO(exisiting);
		for (CovidData covid : series) {
			covid.setLastUpdate(date);
			ts.squash(covid, date);
		}
		buildEurope(ts, date);
		return ts;
	}

	private void buildEurope(TimeSeriesDAO ts, String date) throws ParseException {

		ArrayList<String> countries = ts.getEu();
		Map<String, List<CovidDataDAO>> data = ts.getAllCountries();
		ArrayList<CovidDataDAO> euroList = new ArrayList<CovidDataDAO>();
		CovidDataDAO aggData = new CovidDataDAO();

		for (String country : countries) {

			List<CovidDataDAO> d = data.get(country);
			
			if (d != null) {
				
				if(dateHolder.get(date)!=null) {
					aggData = dateHolder.get(date);
				} else {
					aggData = new CovidDataDAO();

				}
				for (CovidDataDAO covidDataDAO : d) {
					if(covidDataDAO.getLastUpdate().equals(date)) {
						aggData.aggregate(covidDataDAO);
					}
				}
				dateHolder.put(date, aggData);				
			}
			
		}

	}

	public void addEurope(TimeSeriesDAO data) {
		
		String euroKey = "Europe";
		List<CovidDataDAO> dateSeries = new ArrayList<>(dateHolder.values());
		data.getAllCountries().put(euroKey, dateSeries);		
	}
}
