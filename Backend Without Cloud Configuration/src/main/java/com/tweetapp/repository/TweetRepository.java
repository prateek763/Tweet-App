package com.tweetapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;

@Repository
public interface TweetRepository extends MongoRepository<TweetDetails, String>{
	
	@Query("{'username' : ?0}")
	public TweetDetails findByUsername(String username);
	
	@Query("{'username' : ?0}")
	public TweetDetails removeByUsername(String username);

}
