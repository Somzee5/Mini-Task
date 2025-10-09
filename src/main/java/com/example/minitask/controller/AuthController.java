package com.example.minitask.controller;

import com.example.minitask.dto.AuthResponseDTO;
import com.example.minitask.service.AuthService;
import com.example.minitask.dto.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;  // 🟩 Add this import

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🟩 Authenticate user and create session
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @RequestBody(required = false) Map<String, String> credentials,
            @jakarta.validation.Valid @RequestBody(required = false) LoginRequestDTO loginBody,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String password) {
        try {
            // Support both JSON and form-data
            if (loginBody != null) {
                userName = loginBody.getUserName();
                password = loginBody.getPassword();
            } else if (credentials != null) {
                userName = credentials.getOrDefault("userName", credentials.get("username"));
                password = credentials.getOrDefault("password", credentials.get("pass"));
            }
            if (userName == null || userName.isBlank() || password == null || password.isBlank()) {
                throw new RuntimeException("userName and password must not be null or blank");
            }

            String token = authService.authenticate(userName, password);
            return ResponseEntity.ok(AuthResponseDTO.success("Login successful", token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponseDTO.failure(e.getMessage()));
        }
    }
}