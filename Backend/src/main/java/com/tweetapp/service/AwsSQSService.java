package com.tweetapp.service;

public interface AwsSQSService {
	
	public void sendMessageToQueue(String msg);
	
}
