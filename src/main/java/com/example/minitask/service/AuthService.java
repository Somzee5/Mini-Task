package com.example.minitask.service;

import com.example.minitask.entity.User;
import com.example.minitask.entity.UserSession;
import com.example.minitask.repository.UserRepository;
import com.example.minitask.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 🟩 Authenticate and create session
    public String authenticate(String userName, String password) {

        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid username or password!");
        }

        User user = userOpt.get();

        // Compare raw vs hashed
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Generate unique session token
        String sessionToken = UUID.randomUUID().toString();

        // Save session record
        UserSession session = new UserSession();
        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        session.setSessionToken(sessionToken);
        userSessionRepository.save(session);

        return sessionToken;
    }
}
