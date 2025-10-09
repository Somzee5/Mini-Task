package com.example.minitask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserRequestDTO {
    @NotBlank(message = "userName must not be null or blank")
    private String userName;

    @NotBlank(message = "password must not be null or blank")
    private String password;

    @NotBlank(message = "firstName must not be null or blank")
    private String firstName;

    @NotBlank(message = "lastName must not be null or blank")
    private String lastName;

    @NotNull(message = "dateOfBirth must not be null")
    private LocalDate dateOfBirth;

    // Getters and setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}