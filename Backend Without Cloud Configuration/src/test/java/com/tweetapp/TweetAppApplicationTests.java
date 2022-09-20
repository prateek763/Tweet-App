package com.tweetapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.TweetServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TweetAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TweetAppApplicationTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TweetServiceImpl tweetServiceImpl;
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeAll
	void initialConfiguration() {
		UserDetails userDetails = new UserDetails("Prateek", "Jaiswal", "jaiswalprateek451@gmail.com", "Jais123", "LoveScience", Long.valueOf("8448635400"));
		log.info("Registering User with details : {}", userDetails);
		String registerUser = tweetServiceImpl.registerUser(userDetails);
		//Tweet tweet = new Tweet("Testing the working of Application", false, new Date(), null);
		//Tweet tweet1 = new Tweet("Testing the working of Application again", false, new Date(), null);
		//log.info("Post a tweet for test : {} with post {}", "Jais123", tweet);
		//String postNewTweet = tweetServiceImpl.postNewTweet("Jais123", tweet.getTweet());
		//String postNewTweet1 = tweetServiceImpl.postNewTweet("Jais123", tweet.getTweet());
		log.info("Response reply to tweet of {} with id {} and replied comment {}", "Jais123",0,"Reply to testing tweet");
		String tweetReplied = tweetServiceImpl.replyTweet("Jais123",0,"Reply to testing tweet","Pra123");
		
	}
	
	@Test
	void testRegisterUser() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/register";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		UserDetails userDetails = new UserDetails("Prateek", "Jaiswal", "jaiswalprateek471@gmail.com", "Jais12", "LoveScience", Long.valueOf("8448635400"));
		HttpEntity<UserDetails> entity = new HttpEntity<UserDetails>(userDetails, headers);
		ResponseEntity<String> response = testTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("User registration successful", response.getBody());
	}
	
	@Test
	void testLoginUser() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/login";
		URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("username", "Jais123").queryParam("password", "LoveScience").build().toUri();
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = testTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("Login Successful", response.getBody());
	}
	
	@Test
	void testForgetPassword() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais123/forget";
		URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("password", "LovePhysics").build().toUri();
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = testTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("Password Reset", response.getBody());
	}

	@Test
	void testGetAllTweets() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/all";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<TweetDetails>> response = testTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<TweetDetails>>() {
        });
		System.out.println(response.getBody().get(2));
		//TweetDetails expectedTweet = new TweetDetails("Jais123", Arrays.asList(new Tweet("Testing the working of Application", false, new Date(), null),new Tweet("Testing the working of Application again", false, new Date(), null)));
		String expectedTweet = response.getBody().get(2).getTweets().get(0).getTweet();
		//assertTrue(response.getBody().contains(expectedTweet));
		assertEquals("Testing the working of Application", expectedTweet);
	}
	
	@Test
	void testGetAllUsers() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/users/all";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<UserDetails>> response = testTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserDetails>>() {
        });
		System.out.println(response.getBody());
		UserDetails expectedUser = new UserDetails("Prateek", "Jaiswal", "jaiswalprateek451@gmail.com", "Jais123", "LoveScience", Long.valueOf("8448635400"));
		assertTrue(response.getBody().contains(expectedUser));
	}

	@Test
	void testGetUser() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/user/search/Jais123";
		//URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("username", "Jais12").build().toUri();
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<UserDetails> response = testTemplate.exchange(url, HttpMethod.GET, entity, UserDetails.class);
		System.out.println(response.getBody());
		UserDetails expectedUser = new UserDetails("Prateek", "Jaiswal", "jaiswalprateek451@gmail.com", "Jais123", "LoveScience", Long.valueOf("8448635400"));
		assertEquals(expectedUser, response.getBody());
	}

	@Test
	void testGetUserTweets() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais123";
		//URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("username", "Jais12").build().toUri();
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<String>> response = testTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {
        });
		System.out.println(response.getBody());
		String expectedTweet = "Testing the working of Application";
		assertTrue(response.getBody().contains(expectedTweet));
	}

	@Test
	void testPostTweet() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais12/add";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Tweet tweet = new Tweet(1,"JAis12","Test Tweet", false,2, new Date(),2, null);
		HttpEntity<Tweet> entity = new HttpEntity<Tweet>(tweet, headers);
		ResponseEntity<String> response = testTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("New Post Added", response.getBody());
	}

	@Test
	void testUpdateTweet() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais12/update/0";
		URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("tweet", "Test Tweet changed").build().toUri();
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = testTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("Tweet Updated", response.getBody());
	}

	@Test
	void testLikeTweet() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais123/like/0";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = testTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("Tweet Liked", response.getBody());
	}

	@Test
	void testDeleteTweet() {
		String url = "http://localhost:" + port + "/api/v1.0/tweets/Jais123/delete/1";
		TestRestTemplate testTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = testTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		System.out.println(response.getBody());
		assertEquals("Tweet Deleted", response.getBody());
	}
	
	@AfterAll
	void cleanUp() {
		userRepository.deleteById("Jais123");
		userRepository.deleteById("Jais12");
		tweetRepository.deleteById("Jais123");
		tweetRepository.deleteById("Jais12");
	}

}
