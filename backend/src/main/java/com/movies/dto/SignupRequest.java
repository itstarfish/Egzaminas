package com.movies.dto;

import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {

    // Username of the user, must not be blank and should have 3 to 20 characters
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    // Email of the user, must not be blank, should be valid, and have a maximum of 50 characters
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    // Password of the user, must not be blank and should have 6 to 40 characters
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    // Getter for the username
    public String getUsername() {
        return username;
    }

    // Setter for the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for the email
    public String getEmail() {
        return email;
    }

    // Setter for the email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for the password
    public String getPassword() {
        return password;
    }

    // Setter for the password
    public void setPassword(String password) {
        this.password = password;
    }

}
