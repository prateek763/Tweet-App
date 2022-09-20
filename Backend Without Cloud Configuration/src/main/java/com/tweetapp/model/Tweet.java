package com.tweetapp.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "Tweet")
public class Tweet {
	@Transient
	public static final String Sequence_Name = "tweet_sequence";
	
	@Id
	private int id;
	private String username;
	@Size(max=144, message = "Tweet should not be greater than 144 characters")
	private String tweet;
	private boolean isLiked;
	private int likeCount;
	private Date date;
	private int replyCount;
	private List<RepliedTweet> repliedTweets;

}
