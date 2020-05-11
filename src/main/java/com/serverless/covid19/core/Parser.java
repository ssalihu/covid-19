package com.serverless.covid19.core;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.serverless.covid19.dao.TimeSeriesDAO;
import com.serverless.covid19.helper.AWSServiceHelper;
import com.serverless.covid19.helper.Covid19DateHelper;
import com.serverless.covid19.helper.JsonHelper;
import com.serverless.covid19.model.CovidData;
import com.serverless.covid19.service.TimeSeriesService;

public class Parser {
	private static final Logger LOG = LogManager.getLogger(Parser.class);

	public String parseJohnHopkinCovidData() throws ParseException, IOException {
		
		Date startDate = new GregorianCalendar(2020, 02 - 1, 01).getTime();
		Date endDate = new GregorianCalendar().getTime();
		long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
		long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		TimeSeriesDAO data = null;
		RestTemplate restTemplate = new RestTemplate();
		TimeSeriesService timeSeriesService = new TimeSeriesService();
		for (int i = 0; i < days; i++) {
			Date dateInProgress = Covid19DateHelper.adjustDays(startDate, i);
			String formattedDateInProgress = Covid19DateHelper.format(dateInProgress);
			String URL = System.getenv("COVID_API_END_POINT") + formattedDateInProgress;
			try {
				CovidData[] timeSeries = restTemplate.getForObject(URL, CovidData[].class);
				data = timeSeriesService.process(data, timeSeries, formattedDateInProgress);
				timeSeriesService.addEurope(data);
			} catch (Exception ex) {
				LOG.error("Not found for URL " + URL);
			}

		}
		// log.info(JsonHelper.toJson(data));
		AWSServiceHelper.uploadS3(JsonHelper.toJson(data));

		return JsonHelper.toJson(data);
	}


}
