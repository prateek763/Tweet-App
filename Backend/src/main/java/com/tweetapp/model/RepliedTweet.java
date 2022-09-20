package com.tweetapp.model;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamoDBDocument
public class RepliedTweet {
	
	@DynamoDBAttribute
	private String tweet;
	@DynamoDBAttribute
	private String userReplied;
	@DynamoDBAttribute
	private Date date;

}
