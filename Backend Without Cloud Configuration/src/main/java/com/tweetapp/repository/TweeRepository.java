package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.model.Tweet;

public interface TweeRepository extends MongoRepository<Tweet, Integer> {
	
	@Query("{'username' : ?0}")
	public List<Tweet> findAllByUsername(String username);
	
	@Query("{'id' : ?0}")
	public Tweet findById(int id);
	
	@Query("{'id' : ?0,'username' : ?1}")
	public Tweet findByIdAndUsername(int id, String username);

}
