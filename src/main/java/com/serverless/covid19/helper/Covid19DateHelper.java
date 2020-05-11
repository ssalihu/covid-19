package com.serverless.covid19.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Covid19DateHelper {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yy hh:mm");

	public static String format(Date date) {
		return sdf.format(date);
	}

	public static Date parse(String date) throws ParseException {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {

			try {
				return sdf.parse(sdf.format(sdf1.parse(date)));
			} catch (ParseException e1) {
				return sdf.parse(sdf.format(sdf2.parse(date)));
				//return sdf2.parse(date);

			}
		}
	}

	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 
	 * true date1 is after date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	private static boolean compare(String date1, String date2) throws ParseException {
		Date d1 = parse(date1);
		Date d2 = parse(date2);
		return d1.after(d2);
	}

	public static Date adjustDays(Date date, int days) {
		final Calendar cal = dateToCalendar(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	//Convert Date to Calendar
	private static Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	//Convert Calendar to Date
	private static Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

}
