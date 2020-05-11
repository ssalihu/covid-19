package com.serverless.covid19.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	public static String toJson(Object data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	      //Converting the Object to JSONString
	     String jsonString = mapper.writeValueAsString(data);
	     return jsonString;
	}
}
