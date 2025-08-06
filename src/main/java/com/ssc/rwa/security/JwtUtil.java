package com.ssc.rwa.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssc.rwa.model.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private Key secretKey;

	private static final long EXPIRATION_TIME = 86400000; // 1 day

	@PostConstruct
	public void init() {
		System.out.println("JWT_SECRET used: " + secret);

		if (secret == null || secret.length() < 32) {
			throw new IllegalArgumentException("JWT secret key must be at least 32 characters (256 bits)");
		}

		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(User user) {
		return Jwts.builder()
			.setSubject(user.getEmail())
			.claim("roles", user.getRoles().stream().map(Enum::name).collect(Collectors.toList()))
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		}
		catch (JwtException | IllegalArgumentException e) {
			System.err.println("JWT validation failed: " + e.getMessage());
			return false;
		}
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
		return (List<String>) Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("roles");
	}

}
