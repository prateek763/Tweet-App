package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.UserDetails;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, String>{
	
	@Query("{'username' : ?0 , 'password' : ?1 }")
	public UserDetails findByUsernameAndPassword(String username, String password);
	
	@Query("{'username' : ?0}")
	public UserDetails findByUsername(String username);
	
	@Query("{'username' : ?0}")
	public List<UserDetails> deleteByUsername(String username);

}
