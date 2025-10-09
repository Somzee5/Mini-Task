package com.example.minitask.controller;

import com.example.minitask.dto.ApiResponse;
import com.example.minitask.dto.UserRequestDTO;
import com.example.minitask.entity.User;
import com.example.minitask.service.UserService;
import com.example.minitask.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSessionService userSessionService;

    // 🟩 Register new user (requires valid session token)
    @PostMapping
    public ResponseEntity<ApiResponse<User>> registerUser(
            @RequestHeader("X-Session-Token") String token,
            @RequestBody UserRequestDTO requestDTO) {
        try {
            userSessionService.validateSession(token);

            User savedUser = userService.registerUser(
                    requestDTO.getUserName(),
                    requestDTO.getPassword(),
                    requestDTO.getFirstName(),
                    requestDTO.getLastName(),
                    requestDTO.getDateOfBirth()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User registered successfully", savedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(e.getMessage()));
        }
    }

    // 🟩 Get all users (requires valid session)
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(
            @RequestHeader("X-Session-Token") String token) {
        try {
            userSessionService.validateSession(token);
            List<User> users = userService.getAllUsers();

            return ResponseEntity.ok(ApiResponse.success("Fetched all users successfully", users));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(e.getMessage()));
        }
    }

    // 🟩 Get user by ID (requires valid session)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(
            @RequestHeader("X-Session-Token") String token,
            @PathVariable UUID id) {
        try {
            userSessionService.validateSession(token);

            return userService.getUserById(id)
                    .<ResponseEntity<ApiResponse<?>>>map(user ->
                            ResponseEntity.ok(ApiResponse.success("User found", user)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ApiResponse.failure("User not found")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(e.getMessage()));
        }
    }
}
