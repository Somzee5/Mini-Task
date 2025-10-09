package com.example.minitask.service;

import com.example.minitask.entity.User;
import com.example.minitask.entity.UserSession;
import com.example.minitask.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    public UserSession createSession(User user) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionToken(UUID.randomUUID().toString());
        session.setCreatedAt(LocalDateTime.now());
        return userSessionRepository.save(session);
    }

    public void validateSession(String token) {
        Optional<UserSession> optionalSession = userSessionRepository.findBySessionToken(token);

        if (optionalSession.isEmpty()) {
            throw new RuntimeException("Invalid or missing session token");
        }

        UserSession session = optionalSession.get();
        LocalDateTime createdAt = session.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        Duration diff = Duration.between(createdAt, now);
        if (diff.toMinutes() > 5) {
            throw new RuntimeException("Session expired. Please log in again.");
        }
    }


}
