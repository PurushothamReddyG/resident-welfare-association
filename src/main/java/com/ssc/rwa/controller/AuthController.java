package com.ssc.rwa.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssc.rwa.dto.AuthResponse;
import com.ssc.rwa.dto.LoginRequest;
import com.ssc.rwa.dto.UserRegistrationRequest;
import com.ssc.rwa.model.User;
import com.ssc.rwa.repo.UserRepository;
import com.ssc.rwa.security.JwtUtil;
import com.ssc.rwa.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private final UserService userService;        // <-- add this

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegistrationRequest dto,
            BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Map.of("error", errorMessages));
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            response.put("message", "User already exists");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            userService.registerUser(dto);
            response.put("message", "User registered successfully. Please check your email for verification.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Failed to register user");
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        String result = userService.verify(token);

        switch (result) {
            case "verified":
                return ResponseEntity.ok(Map.of("message", "Email verified successfully."));
            case "expired":
                return ResponseEntity.badRequest().body(Map.of("error", "Verification token expired."));
            default:
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid verification token."));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(userOpt.get());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
