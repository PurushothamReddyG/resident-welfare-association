package com.ssc.rwa.service;

import java.io.IOException;

import com.ssc.rwa.dto.UserRegistrationRequest;
import jakarta.mail.MessagingException;

public interface UserService {
    void registerUser(UserRegistrationRequest dto) throws MessagingException;
    String verify(String token);
    void resendVerification(String email) throws MessagingException, IOException;
}
