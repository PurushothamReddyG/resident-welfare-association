package com.ssc.rwa.dto;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import com.ssc.rwa.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "User registration request payload")
public class UserRegistrationRequest {

	@NotBlank(message = "Username is mandatory")
	@Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
	@Schema(description = "Unique username for the account", example = "john_doe")
	private String userName;

    @Indexed(unique = true)
	@NotBlank(message = "Email is required")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format")
	@Schema(description = "Email of the user", example = "user@example.com")
	private String email;

	@NotBlank(message = "Password is required")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters, contain uppercase, lowercase, number, and special character")
	@Schema(description = "Strong password with uppercase, lowercase, number, and special character", example = "Pass@1234")
	private String password;

	@NotBlank(message = "Confirm password is required")
	@Schema(description = "Password confirmation to match with the password field", example = "Pass@1234")
	private String confirmPassword;

	@NotEmpty(message = "At least one role must be specified")
	@Schema(description = "List of roles assigned to the user")
	private List<Role> roles;

	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
	@Schema(description = "10-digit mobile number", example = "9876543210")
	private String mobileNumber;
}
