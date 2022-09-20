package com.tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.model.Response;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.UserDetails;
import com.tweetapp.service.AwsSqsServiceIpml;
import com.tweetapp.service.TweetAppMessages;
import com.tweetapp.service.TweetServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {
	
	@Autowired
	private TweetServiceImpl tweetServiceImpl;
	
	@Autowired
	private AwsSqsServiceIpml sqsService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDetails userDetails) {
		log.info("Request resigterUser : {}", userDetails);
		String registerUser = tweetServiceImpl.registerUser(userDetails);
		if (registerUser.equalsIgnoreCase(TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage())) {
			log.info("Registered user with details : {}", userDetails);
			//kafkaTemplate.send(topic,"Response resigterUser : " + registerUser);
			sqsService.sendMessageToQueue("Response resigterUser with username "+userDetails.getUsername()+" : " + registerUser);
			return new ResponseEntity<>(new Response(registerUser), HttpStatus.CREATED);
		} else {
			log.info("Not able to register user with details : {}", registerUser);
			//kafkaTemplate.send(topic,"Response resigterUser : " + registerUser);
			sqsService.sendMessageToQueue("Response resigterUser with username "+userDetails.getUsername()+" : " + registerUser);
			return new ResponseEntity<>(new Response(registerUser), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/login")
	public ResponseEntity<Response> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		log.info("Request Login User : {} with password {}", username,password);
		String result = tweetServiceImpl.login(username, password);
		if (result.equalsIgnoreCase(TweetAppMessages.LOGIN_SUCCESSFUL.getMessage())) {
			log.info("Logged in user with details : {} and {}", username,password);
			
			//kafkaTemplate.send(topic,"Response resigterUser : " + result);
			sqsService.sendMessageToQueue("Response loginUser with username "+username+" : " + result);
			return new ResponseEntity<>(new Response(result), HttpStatus.OK);
		} else {
			log.info("Not able to log in user with details : {} and {}", username, password);
			//kafkaTemplate.send(topic,"Response resigterUser : " + result);
			sqsService.sendMessageToQueue("Response loginUser with username "+username+" : " + result);
			return new ResponseEntity<>(new Response(result), HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@GetMapping("/{username}/forget")
	public ResponseEntity<?> forgetPassword(@PathVariable("username") String username,
			@RequestParam("password") String password) {
		log.info("Request Forget password : {} with new password {}", username,password);
		String result = tweetServiceImpl.forgetPassword(username, password);
		if (result.equalsIgnoreCase(TweetAppMessages.PASSWORD_RESET.getMessage())) {
			log.info("Reset password with details : {} with {}", username,password);
			//kafkaTemplate.send(topic,"Response resigterUser : " + result);
			sqsService.sendMessageToQueue("Response forgotPassword with username "+username+" : " + result);
			return new ResponseEntity<>(new Response(result), HttpStatus.OK);
		} else {
			log.info("Invalid details : {} or {}", username, password);
			//kafkaTemplate.send(topic,"Response resigterUser : " + result);
			sqsService.sendMessageToQueue("Response forgotPassword with username "+username+" : " + result);
			return new ResponseEntity<>(new Response(result), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllTweets() {
		try {
			log.info("Request to get all tweets");
			List<Tweet> allTweets = tweetServiceImpl.getAllTweets();
			//kafkaTemplate.send(topic,"Response getAllTweets : " + allTweets);
			sqsService.sendMessageToQueue("Response getAllTweets : All tweets fetched successfully");
			log.info("Response got all tweets : {}", allTweets);
			return new ResponseEntity<>(allTweets, HttpStatus.OK);
		} catch (Exception e) {
			log.info("Response getAllTweets : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response getAllTweets : "+e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/users/all")
	public ResponseEntity<?> getAllUsers() {
		try {
			log.info("Request to get all tweets");
			List<UserDetails> allUsers = tweetServiceImpl.getAllUsers();
			sqsService.sendMessageToQueue("Response getAllUsers : All users fetched successfully");
			log.info("Response got all users : {}", allUsers);
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		} catch (Exception e) {
			log.info("Response getAllUsers : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response getAllUsers : "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/search/{username}")
	public ResponseEntity<?> searchByUsername(@PathVariable("username") String username) {
		try {
			log.info("Request searchByUsername : {}", username);
			sqsService.sendMessageToQueue("Request search user  with username : "+username);
			UserDetails userByUsername = tweetServiceImpl.getUserByUsername(username);
			if (userByUsername != null) {
				log.info("Response user fetched successfully : {}", userByUsername);
				sqsService.sendMessageToQueue("Response search user  with "+username+" : User fetched successfully");
				return new ResponseEntity<>(userByUsername, HttpStatus.OK);
			} else {
				log.info("Response username not fount : {}", userByUsername);
				sqsService.sendMessageToQueue("Response search user  with "+username+" : User not found");
				return new ResponseEntity<>(new Response("Username "+username+" not found"), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("Response searchByUsername : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response search user  with "+username+" : "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getAllTweetsPerUser(@PathVariable("username") String username) {
		try {
			log.info("Request get all tweets of {} user ", username);
			sqsService.sendMessageToQueue("Request Tweets  of "+username);
			List<Tweet> tweetsOfSpecificUser = tweetServiceImpl.tweetsOfSpecificUser(username);
			if (!tweetsOfSpecificUser.isEmpty()) {
				log.info("Response Fetched tweets of {} : {}", username, tweetsOfSpecificUser);
				sqsService.sendMessageToQueue("Response Tweets  of "+username+" : Tweets fetched successfully");
				return new ResponseEntity<>(tweetsOfSpecificUser, HttpStatus.OK);
			} else {
				log.info("Response {} has no tweet", username);
				sqsService.sendMessageToQueue("Response Tweets  of "+username+" : No tweets found");
				return new ResponseEntity<>(new Response(TweetAppMessages.NO_TWEETS_YET.getMessage()), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("Response getAllTweetsPerUser {}", e.getMessage());
			sqsService.sendMessageToQueue("Response Tweets  of "+username+" : "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{username}/add")
	public ResponseEntity<?> postNewTweet(@PathVariable("username") String username, @RequestBody Tweet tweet) {
		try {
			log.info("Request post a Tweet : {} with post {}", username, tweet);
			sqsService.sendMessageToQueue("Request post tweet by "+username);
			String postNewTweet = tweetServiceImpl.postNewTweet(username, tweet.getTweet());
			if (postNewTweet.equalsIgnoreCase(TweetAppMessages.NEW_POST_SUCCESSFUL.getMessage())) {
				sqsService.sendMessageToQueue("Response Tweets  of "+username+" : Tweets posted successfully");
				log.info("Response Tweet posted successfully : {}", postNewTweet);
				return new ResponseEntity<>(new Response(postNewTweet), HttpStatus.OK);
			} else {
				log.info("Response postNewTweet : {}", postNewTweet);
				sqsService.sendMessageToQueue("Response Tweets  of "+username+" : Tweets not posted successfully");
				return new ResponseEntity<>(new Response(postNewTweet), HttpStatus.METHOD_NOT_ALLOWED);
			}
		} catch (Exception e) {
			log.info("Response postNewTweet : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response Tweets  of "+username+" : "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<?> updateTweet(@PathVariable("username") String username, @PathVariable("id") String id,
			@RequestBody String tweet) {
		try {
			log.info("Request update tweet of {} with id {} to {}", username,id,tweet);
			sqsService.sendMessageToQueue("Request update tweet  of "+username+" with id "+id);
			String updateTweet = tweetServiceImpl.updateTweet(username, id, tweet);
			if (updateTweet.equalsIgnoreCase(TweetAppMessages.TWEET_UPDATED.getMessage())) {
				log.info("Response updated tweet of {} with id {} to {} successfully", username,id,tweet);
				sqsService.sendMessageToQueue("Response update tweet  of "+username+" with id "+id+": "+updateTweet);
				return new ResponseEntity<>(new Response(updateTweet), HttpStatus.OK);
			} else {
				log.info("Response updateTweet : {}", updateTweet);
				sqsService.sendMessageToQueue("Response update tweet  of "+username+" with id "+id+": "+updateTweet);
				return new ResponseEntity<>(new Response(updateTweet), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Response updateTweet : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response update tweet  of "+username+" with id "+id+": "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<?> deleteTweet(@PathVariable("username") String username, @PathVariable("id") String id) {
		try {
			log.info("Request delete tweet of {} with id {}", username,id);
			sqsService.sendMessageToQueue("Request delete tweet  of "+username+" with id "+id);
			String deleteTweet = tweetServiceImpl.deleteTweet(username, id);
			if (deleteTweet.equalsIgnoreCase(TweetAppMessages.TWEET_DELETED.getMessage())) {
				log.info("Response  deleted tweet of {} with id {} successfully", username,id);
				sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+deleteTweet);
				return new ResponseEntity<>(new Response(deleteTweet), HttpStatus.OK);
			} else {
				log.info("Response deleteTweet : {}", deleteTweet);
				sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+deleteTweet);
				return new ResponseEntity<>(new Response(deleteTweet), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Response deleteTweet : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{username}/like/{id}")
	public ResponseEntity<?> likeTweet(@PathVariable("username") String username, @PathVariable("id") String id) {
		try {
			log.info("Request like tweet of {} with id {}", username,id);
			sqsService.sendMessageToQueue("Request like tweet  of "+username+" with id "+id);
			String likeTweet = tweetServiceImpl.likeTweet(username, id);
			if (likeTweet.equalsIgnoreCase(TweetAppMessages.TWEET_LIKED.getMessage())) {
				log.info("Response liked tweet of {} with id {} successfully", username,id);
				sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+likeTweet);
				return new ResponseEntity<>(new Response(likeTweet), HttpStatus.OK);
			} else {
				log.info("Response likeTweet : {}", likeTweet);
				sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+likeTweet);
				return new ResponseEntity<>(new Response(likeTweet), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Response likeTweet : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response delete tweet  of "+username+" with id "+id+": "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<?> replyToTweet(@PathVariable("username") String username, @PathVariable("id") String id,
			@RequestParam("tweet") String tweet, @RequestParam("user") String userReplied) {
		try {
			log.info("Response reply to tweet of {} with id {} and replied comment {}", username,id,tweet);
			sqsService.sendMessageToQueue("Request reply tweet  of "+username+" with id "+id);
			String tweetReplied = tweetServiceImpl.replyTweet(username, id, tweet,userReplied);
			if (tweetReplied.equalsIgnoreCase(TweetAppMessages.TWEET_REPLIED.getMessage())) {
				log.info("Response reply to tweet of {} with id {} and replied comment {}", username,id,tweet);
				sqsService.sendMessageToQueue("Response reply tweet  of "+username+" with id "+id+": "+tweetReplied);
				return new ResponseEntity<>(new Response(tweetReplied), HttpStatus.OK);
			} else {
				log.info("Response replyToTweet : {}", tweetReplied);
				sqsService.sendMessageToQueue("Response reply tweet  of "+username+" with id "+id+": "+tweetReplied);
				return new ResponseEntity<>(new Response(tweetReplied), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Response replyToTweet : {}", e.getMessage());
			sqsService.sendMessageToQueue("Response reply tweet  of "+username+" with id "+id+": "+e.getMessage());
			return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
}
