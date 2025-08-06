package com.ssc.rwa.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssc.rwa.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);

}
