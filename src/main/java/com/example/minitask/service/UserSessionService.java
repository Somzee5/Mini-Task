package com.example.minitask.service;

import com.example.minitask.entity.User;
import com.example.minitask.entity.UserSession;
import com.example.minitask.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    private static final long SESSION_VALIDITY_MINUTES = 10;

    /**
     * Validate the provided session token and return the corresponding user.
     * Throws RuntimeException if token is invalid or expired.
     */
    public User validateSession(String sessionToken) {
        if (sessionToken == null || sessionToken.isBlank()) {
            throw new RuntimeException("Missing session token");
        }

        Optional<UserSession> sessionOpt = userSessionRepository.findBySessionToken(sessionToken);
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Invalid session token");
        }

        UserSession session = sessionOpt.get();

        // Check if token expired
        LocalDateTime createdAt = session.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        long minutesElapsed = Duration.between(createdAt, now).toMinutes();

        if (minutesElapsed > SESSION_VALIDITY_MINUTES) {
            throw new RuntimeException("Session expired, please log in again");
        }

        return session.getUser();
    }

    /**
     * (Optional) Utility method to get latest session of a user.
     */
    public Optional<UserSession> getLatestSession(User user) {
        return userSessionRepository.findTopByUserOrderByCreatedAtDesc(user);
    }
}
