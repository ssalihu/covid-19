package com.serverless.covid19.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestMe {

	public static void main(String argsp[]) throws ParseException {
		String date = "3/22/20 23:45";
		date = "2020-02-24";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm");
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.parse(date).toString());
	}
}
