package com.ssc.rwa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssc.rwa.dto.AuthResponse;
import com.ssc.rwa.dto.LoginRequest;
import com.ssc.rwa.model.Role;
import com.ssc.rwa.model.User;
import com.ssc.rwa.repo.UserRepository;
import com.ssc.rwa.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Handles registration and authentication")
public class AuthController {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
		Map<String, String> response = new HashMap<>();
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			response.put("message", "User already exists");
			return ResponseEntity.badRequest().body(response);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			user.setRoles(List.of(Role.VILLA_OWNER)); // Default role
		}

		userRepository.save(user);
		response.put("message", "User registered successfully");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	@Operation(summary = "Login and get JWT token")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}

		String token = jwtUtil.generateToken(user);
		return ResponseEntity.ok(new AuthResponse(token));
	}

}
