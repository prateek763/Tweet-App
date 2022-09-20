package com.tweetapp.service;

import java.util.List;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;

public interface TweetService {
	
	public String registerUser(UserDetails user);

	public String login(String username, String password);

	public String forgetPassword(String username, String newPassword);

	public List<Tweet> getAllTweets();

	public List<UserDetails> getAllUsers();

	public UserDetails getUserByUsername(String username);

	public List<Tweet> tweetsOfSpecificUser(String username);

	public String postNewTweet(String username, String tweet);

	public String updateTweet(String username, String id, String tweet);

	public String deleteTweet(String username, String id);

	public String likeTweet(String username, String id);

	public String replyTweet(String username, String id,String tweet, String userReplied);

}
