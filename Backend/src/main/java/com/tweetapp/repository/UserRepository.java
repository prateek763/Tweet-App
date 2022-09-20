package com.tweetapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.UserDetails;

@Repository
public class UserRepository {
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void save(UserDetails user)
	{
		mapper.save(user);
	}
	
	public UserDetails findByUsername(String username) {
		UserDetails user = mapper.load(UserDetails.class, username);
		return user;
	}
	
	public List<UserDetails> findAll()
	{
		List<UserDetails> scanList = mapper.scan(UserDetails.class, new DynamoDBScanExpression());
		/*for(TweetDetails tweetDetails : scanList)
		{
			tweetDetailsList.add(tweetDetails);
		}*/
		
		return scanList;
	}
}
