package com.ssc.rwa.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	private String id;

	private String userName;

	private String email;

	private String password;

	private List<Role> roles;
	
	private String mobileNumber;
	
	@Builder.Default
    private boolean enabled = false; // false until email is verified
}
