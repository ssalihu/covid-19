package com.serverless;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.covid19.core.Parser;
import com.serverless.covid19.helper.AWSServiceHelper;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		
		Parser p = new Parser();
		String responsPayload = "";
		try {
			responsPayload = p.parseJohnHopkinCovidData();
			AWSServiceHelper.sendStatusSNS(responsPayload);
		} catch (ParseException e) {
			e.printStackTrace();
			AWSServiceHelper.sendStatusSNS("Error: "+ e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			AWSServiceHelper.sendStatusSNS("Error: "+ e.getMessage());
		}
		// Publish a message to an Amazon SNS topic.
				
		LOG.info("received: {}", input);
		Response responseBody = new Response("Go Serverless v1.x! Your function executed successfully!", input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();

		
	}


}
