package com.tweetapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.model.RepliedTweet;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.repository.TweeRepository;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private TweeRepository repository;
	
	@Autowired
	private SequenceGeneratorService service;

	@Override
	public String registerUser(UserDetails user) {
		if(user.getUsername() != null && user.getPassword() !=null) {
			userRepository.save(user);
			log.info(TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage());
			return TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage();
		}
		return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String login(String username, String password) {
		if (username != null && password != null) {
			UserDetails userDetails = getUserByUsername(username);
			if (userDetails != null && userDetails.getPassword().equalsIgnoreCase(password))
				return TweetAppMessages.LOGIN_SUCCESSFUL.getMessage();
			else
				return TweetAppMessages.INVALID_CREDENTIALS.getMessage();

		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String forgetPassword(String username, String newPassword) {
		if (username != null && newPassword != null) {
			UserDetails findByUsername = getUserByUsername(username);
			if (findByUsername != null) {
				findByUsername.setPassword(newPassword);
				userRepository.save(findByUsername);
				return TweetAppMessages.PASSWORD_RESET.getMessage();

			} else
				return TweetAppMessages.INVALID_USERNAME.getMessage();

		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public List<Tweet> getAllTweets() {
		return repository.findAll();
	}

	@Override
	public List<UserDetails> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails getUserByUsername(String username) {
		UserDetails findByUsername = userRepository.findByUsername(username);
		return findByUsername;
	}

	@Override
	public List<Tweet> tweetsOfSpecificUser(String username) {
		/*List<String> tweetList = new ArrayList<String>();
		List<Tweet> tweetsOfSpecificUser = getTweetListPerUser(username);
		for (Tweet tweet : tweetsOfSpecificUser) {
			tweetList.add(tweet.getTweet());
		}*/

		return repository.findAllByUsername(username);
	}

	@Override
	public String postNewTweet(String username, String tweet) {
		if (username != null && tweet != null) {
			//List<Tweet> tweetList = getTweetListPerUser(username);
			/*Tweet newTweet = new Tweet(tweet, false, new Date(), null);
			tweetList.add(newTweet);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);*/
			Tweet newTweet = new Tweet(service.getSequenceNumber(Tweet.Sequence_Name), username, tweet, false, 0, new Date(), 0, null);
			repository.save(newTweet);
			return TweetAppMessages.NEW_POST_SUCCESSFUL.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String updateTweet(String username, int id, String tweet) {
		if (username != null && tweet != null) {
			Tweet tweets = repository.findById(id);
			tweets.setTweet(tweet);
			repository.save(tweets);
			return TweetAppMessages.TWEET_UPDATED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String deleteTweet(String username, int id) {
		if (username != null) {
			//repository.removeById(id);
			repository.deleteById(id);
			return TweetAppMessages.TWEET_DELETED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String likeTweet(String username, int id) {
		if (username != null) {
			/*List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet tweet = tweetList.get(id);
			tweet.setLiked(Boolean.TRUE);
			tweetList.set(id, tweet);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);*/
			Tweet tweet= repository.findByIdAndUsername(id,username);
			tweet.setLiked(true);
			tweet.setLikeCount(tweet.getLikeCount()+1);
			repository.save(tweet);
			return TweetAppMessages.TWEET_LIKED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String replyTweet(String username, int id, String tweet,String userReplied) {
		List<RepliedTweet> repliedTweets;
		if (username != null && tweet != null) {
			/*List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet tweetInfo = tweetList.get(id);
			repliedTweets = tweetInfo.getRepliedTweets();*/
			Tweet tweetInfo=repository.findByIdAndUsername(id, username);
			repliedTweets=tweetInfo.getRepliedTweets();
			if (repliedTweets != null)
				repliedTweets.add(new RepliedTweet(tweet, userReplied, new Date()));
			else {
				repliedTweets = new ArrayList<>();
				repliedTweets.add(new RepliedTweet(tweet, userReplied, new Date()));
			}
			tweetInfo.setReplyCount(tweetInfo.getReplyCount()+1);		tweetInfo.setRepliedTweets(repliedTweets);
			/*tweetList.set(id, tweetInfo);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);*/
			repository.save(tweetInfo);
			return TweetAppMessages.TWEET_REPLIED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}
	
	private List<Tweet> getTweetListPerUser(String username) {
		TweetDetails findByUsername = tweetRepository.findByUsername(username);
		List<Tweet> tweetList = new ArrayList<>();
		if (findByUsername != null) {
			tweetList = findByUsername.getTweets();
		} else
			tweetList = new ArrayList<>();
		return tweetList;
	}

}
