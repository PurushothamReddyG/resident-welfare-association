package com.ssc.rwa.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssc.rwa.model.VerificationToken;

public interface TokenRepository extends MongoRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUserId(String userId);
    List<VerificationToken> findByExpiryDateBefore(Date date);
}
