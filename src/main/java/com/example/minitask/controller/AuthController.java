package com.example.minitask.controller;

import com.example.minitask.dto.AuthResponseDTO;
import com.example.minitask.service.AuthService;
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
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String password) {
        try {
            // Support both JSON and form-data
            if (credentials != null) {
                // Support both camelCase and lowercase keys often used in clients
                userName = credentials.getOrDefault("userName", credentials.get("username"));
                password = credentials.getOrDefault("password", credentials.get("pass"));
            }
            if (userName == null || password == null) {
                throw new RuntimeException("Missing userName or password");
            }

            String token = authService.authenticate(userName, password);
            return ResponseEntity.ok(AuthResponseDTO.success("Login successful", token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponseDTO.failure(e.getMessage()));
        }
    }
}