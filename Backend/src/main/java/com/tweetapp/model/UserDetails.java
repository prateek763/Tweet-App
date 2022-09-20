package com.tweetapp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "UserDetails")
public class UserDetails {
	
	@DynamoDBAttribute
	private String first_name;
	@DynamoDBAttribute
	private String last_name;
	@DynamoDBAttribute
	private String email;
	@DynamoDBHashKey
	private String username;
	@DynamoDBAttribute
	private String password;
	@DynamoDBAttribute
	private long number;

}
