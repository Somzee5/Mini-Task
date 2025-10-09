package com.example.minitask.dto;

public class AuthResponseDTO {
    private boolean success;
    private String message;
    private String token;

    public AuthResponseDTO() {}

    public AuthResponseDTO(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public static AuthResponseDTO success(String message, String token) {
        return new AuthResponseDTO(true, message, token);
    }

    public static AuthResponseDTO failure(String message) {
        return new AuthResponseDTO(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getToken() { return token; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setToken(String token) { this.token = token; }
}
