package com.ssc.rwa.payload;

import lombok.Data;

@Data
public class RegisterRequest {

	private String username;

	private String password;

	private String role; // e.g., ROLE_VILLA_OWNER or ROLE_ASSOCIATION_MEMBER

}
