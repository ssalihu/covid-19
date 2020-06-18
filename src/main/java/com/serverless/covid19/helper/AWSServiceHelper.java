package com.serverless.covid19.helper;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class AWSServiceHelper {
	
	private static final Logger LOG = LogManager.getLogger(AWSServiceHelper.class);
	
	public static void sendStatusSNS(String data) {
		@SuppressWarnings("deprecation")
		AmazonSNSClient snsClient = new AmazonSNSClient();
		String topicArn = System.getenv("TOPIC_ARN");

		final String msg = " Covid parsed data from JHU successfully." ;
		final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
		final PublishResult publishResponse = snsClient.publish(publishRequest);

		// Print the MessageId of the message.
		LOG.info("MessageId: " + publishResponse.getMessageId());
	}
	
	public static void uploadS3(String data) throws IOException {
		// Regions clientRegion = Regions.DEFAULT_REGION;
		String bucketName = System.getenv("S3_BUCKET");
		String stringObjKeyName = System.getenv("DATA_FILE");
		// String fileObjKeyName = "";
		// String fileName = "*** Path to file to upload ***";

		try {
			// This code expects that you have AWS credentials set up per:
			// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					// .withRegion(clientRegion)
					.build();

			// Upload a text string as a new object.
			s3Client.putObject(bucketName, stringObjKeyName, data);

			// Upload a file as a new object with ContentType and title specified.
			// PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName,
			// new File(fileName));
			// ObjectMetadata metadata = new ObjectMetadata();
			// metadata.setContentType("plain/text");
			// metadata.addUserMetadata("title", "someTitle");
			// request.setMetadata(metadata);
			// s3Client.putObject(request);
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
	}
}
