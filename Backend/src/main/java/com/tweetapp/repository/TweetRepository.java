package com.tweetapp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.tweetapp.model.Tweet;

@Repository
public class TweetRepository {
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void save(Tweet tweet)
	{
		mapper.save(tweet);
	}

	public List<Tweet> findAllByUsername(String username)
	{
		List<Tweet> tweets= new ArrayList<Tweet>();
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put(":var8", new AttributeValue().withS(username));
	    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("username = :var8").withExpressionAttributeValues(eav);
		List<Tweet> scanList = mapper.scan(Tweet.class, scanExpression);
		/*for(Tweet tweet : scanList)
		{
			tweets.add(tweet);
		}*/
		
		return scanList;
	}
	
	public List<Tweet> findAll()
	{
		List<Tweet> tweetDetailsList = new ArrayList<>();
		List<Tweet> scanList = mapper.scan(Tweet.class, new DynamoDBScanExpression());
		/*for(TweetDetails tweetDetails : scanList)
		{
			tweetDetailsList.add(tweetDetails);
		}*/
		
		return scanList;
	}
	
	public void updateTweet(String username, String id,Tweet tweet)
	{
		List<Tweet> tweets= new ArrayList<Tweet>();
		/*Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put("user", new AttributeValue().withS(username));
	    eav.put("tid", new AttributeValue().withN(String.valueOf(id)));*/
	    DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpectedEntry("id",
	    		new ExpectedAttributeValue(new AttributeValue().withS(id)));
		mapper.save(tweet, saveExpression);
		/*for(Tweet tweet : scanList)
		{
			tweets.add(tweet);
		}*/
	}
	
	public void deleteById(String username, String id) {
		Tweet tweet = mapper.load(Tweet.class, id);
		mapper.delete(tweet);
	}
	
	public Tweet findById(String id) {
		Tweet tweet = mapper.load(Tweet.class, id);
		return tweet;
	}
}
