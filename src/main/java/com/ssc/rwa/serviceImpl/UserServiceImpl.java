package com.ssc.rwa.serviceImpl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssc.rwa.dto.UserRegistrationRequest;
import com.ssc.rwa.model.Role;
import com.ssc.rwa.model.User;
import com.ssc.rwa.model.VerificationToken;
import com.ssc.rwa.repo.TokenRepository;
import com.ssc.rwa.repo.UserRepository;
import com.ssc.rwa.service.EmailService;
import com.ssc.rwa.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final ModelMapper modelMapper = new ModelMapper();

	@Value("${app.base-url}")
	private String baseUrl; // Inject from application.properties

	@Override
	public void registerUser(UserRegistrationRequest dto) throws MessagingException {
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("User already exists");
		}

		User user = modelMapper.map(dto, User.class);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(dto.getRoles() == null || dto.getRoles().isEmpty()
				? java.util.List.of(Role.VILLA_OWNER)
				: dto.getRoles());
		user.setEnabled(false);
		userRepository.save(user);

		// Create verification token with 24-hour expiry
		String token = UUID.randomUUID().toString();
		Date expiryDate = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
		VerificationToken verificationToken = new VerificationToken(token, user.getId(), expiryDate);
		tokenRepository.save(verificationToken);

		String verificationLink = String.format("%s/auth/verify?token=%s", baseUrl, token);

		// Prepare placeholders for email template
		Map<String, String> placeholders = Map.of(
			"userName", dto.getUserName(),
			"verificationLink", verificationLink
		);

		String htmlContent = emailService.buildEmailFromTemplate("emailVerification", placeholders);

		try {
			emailService.sendEmail(dto.getEmail(), "Email Verification", htmlContent);
		} catch (Exception e) {
			log.error("Failed to send verification email to {}: {}", dto.getEmail(), e.getMessage(), e);
			throw new RuntimeException("Failed to send verification email. Please try again later.");
		}
	}

	@Override
	public String verify(String token) {
		Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
		if (optionalToken.isEmpty()) return "invalid";

		VerificationToken verificationToken = optionalToken.get();

		if (verificationToken.getExpiryDate().before(new Date())) {
			return "expired";
		}

		Optional<User> userOpt = userRepository.findById(verificationToken.getUserId());
		if (userOpt.isEmpty()) return "invalid";

		User user = userOpt.get();
		user.setEnabled(true);
		userRepository.save(user);

		tokenRepository.delete(verificationToken);

		return "verified";
	}

	@Override
	public void resendVerification(String email) throws MessagingException {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty() || optionalUser.get().isEnabled()) {
			return; // Skip if user not found or already enabled
		}

		// Remove old token if exists
		tokenRepository.deleteByUserId(optionalUser.get().getId());

		// Create new token with 24-hour expiry
		String token = UUID.randomUUID().toString();
		Date expiryDate = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
		VerificationToken verificationToken = new VerificationToken(token, optionalUser.get().getId(), expiryDate);
		tokenRepository.save(verificationToken);

		String verificationLink = baseUrl + "/auth/verify?token=" + token;

		Map<String, String> placeholders = Map.of(
			"userName", optionalUser.get().getUserName(),
			"verificationLink", verificationLink
		);

		String htmlContent = emailService.buildEmailFromTemplate("emailVerification", placeholders);

		try {
			emailService.sendEmail(email, "Email Verification", htmlContent);
		} catch (Exception e) {
			log.error("Failed to resend verification email to {}: {}", email, e.getMessage(), e);
			throw new RuntimeException("Email sending failed", e);
		}
	}
}
