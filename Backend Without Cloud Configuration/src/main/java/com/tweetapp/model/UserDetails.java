package com.tweetapp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserDetails")
public class UserDetails {
	@NotNull(message = "First Name cannot be empty")
	private String first_name;
	@NotNull(message = "Second Name cannot be empty")
	private String last_name;
	@Indexed(unique = true)
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid email address")
	private String email;
	@Id
	private String username;
	private String password;
	private long number;

}
