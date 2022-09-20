package com.tweetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AwsSqsServiceIpml implements AwsSQSService {

	private static final String ENDPOINT = "https://sqs.us-west-2.amazonaws.com/098022254153/TweetAppPrateekQueue";
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Override
	public void sendMessageToQueue(String msg) {
		queueMessagingTemplate.send(ENDPOINT,MessageBuilder.withPayload(msg).build());		
	}

}
