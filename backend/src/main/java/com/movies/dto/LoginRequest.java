package com.movies.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * A DTO class for handling login requests.
 */
public class LoginRequest {

    // User's username, cannot be blank
    @NotBlank
    private String username;

    // User's password, cannot be blank
    @NotBlank
    private String password;

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}