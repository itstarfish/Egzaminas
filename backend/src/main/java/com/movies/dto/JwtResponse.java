package com.movies.dto;

import java.util.List;

/**
 * A DTO class for handling JWT responses.
 */
public class JwtResponse {

    // JWT token
    private String token;

    // Token type, default is "Bearer"
    private String type = "Bearer";

    // User ID
    private Long id;

    // User username
    private String username;

    // User email
    private String email;

    // List of user roles
    private List<String> roles;

    /**
     * Constructor to initialize all fields of JwtResponse.
     *
     * @param accessToken the access token string
     * @param id          the user's ID
     * @param username    the user's username
     * @param email       the user's email
     * @param roles       the list of user's roles
     */
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Gets the JWT access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * Sets the JWT access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return type;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the token type
     */
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's username.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of user roles.
     *
     * @return the list of roles
     */
    public List<String> getRoles() {
        return roles;
    }
}
