package com.example.minitask.controller;

import com.example.minitask.dto.ApiResponse;
import com.example.minitask.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🟩 Authenticate user and create session
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> authenticate(
            @RequestParam String userName,
            @RequestParam String password) {
        try {
            String token = authService.authenticate(userName, password);
            return ResponseEntity.ok(ApiResponse.success("Login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(e.getMessage()));
        }
    }
}
