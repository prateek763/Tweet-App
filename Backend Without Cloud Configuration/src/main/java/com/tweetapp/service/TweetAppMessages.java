package com.tweetapp.service;

public enum TweetAppMessages {
	
	USER_REGISTRATION_SUCCESSFUL("User registration successful"),
	INVALID_CREDENTIALS("Credentials are invalid"),
	LOGIN_SUCCESSFUL("Login Successful"),
	INVALID_INPUT("Input is invalid, plz enter valid input"),
	INVALID_USERNAME("Username is not valid"),
	PASSWORD_RESET("Password Reset"), 
	NEW_POST_SUCCESSFUL("New Post Added"),
	TWEET_UPDATED("Tweet Updated"), 
	TWEET_DELETED("Tweet Deleted"), 
	TWEET_LIKED("Tweet Liked"), 
	TWEET_REPLIED("Tweet Replied"),
	NO_TWEETS_YET("User has not tweeted yet");

	private final String msg;

	private TweetAppMessages(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}

}
