package com.example.minitask.service;

import com.example.minitask.entity.User;
import com.example.minitask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String userName, String password,
                             String firstName, String lastName,
                             LocalDate dateOfBirth) {

        Optional<User> existing = userRepository.findByUserName(userName);
        if (existing.isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User(userName, hashedPassword, firstName, lastName, dateOfBirth);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
}