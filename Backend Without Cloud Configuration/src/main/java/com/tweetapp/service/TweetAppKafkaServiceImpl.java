package com.tweetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TweetAppKafkaServiceImpl implements TweetAppKafkaService{
	
	/*private static final String TOPIC_NAME = "TweetTopic";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;*/

	@Override
	public void sendTweetData(String data) {

		//kafkaTemplate.send(TOPIC_NAME, data);
	}

}
