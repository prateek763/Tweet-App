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

	public String updateTweet(String username, int id, String tweet);

	public String deleteTweet(String username, int id);

	public String likeTweet(String username, int id);

	public String replyTweet(String username, int id,String tweet, String userReplied);

}
