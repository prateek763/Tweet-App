package com.tweetapp.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepliedTweet {
	
	private String tweet;
	private String userReplied;
	private Date date;

}
