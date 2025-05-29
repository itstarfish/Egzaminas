package com.movies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a User.
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    // Primary key field for the User entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username of the user, must not be blank and limited to 20 characters
    @NotBlank
    @Size(max = 20)
    private String username;

    // Email of the user, must not be blank and valid, limited to 50 characters
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    // Password of the user, must not be blank and limited to 120 characters
    @NotBlank
    @Size(max = 120)
    private String password;

    // Roles associated with the user, represented as a many-to-many relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * Default no-arg constructor for User.
     */
    public User() {
    }

    /**
     * Constructor for creating a User object.
     *
     * @param username The username of the user
     * @param email    The email of the user
     * @param password The password of the user
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The new ID of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the roles associated with the user.
     *
     * @return A set of roles associated with the user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles associated with the user.
     *
     * @param roles A set of new roles to associate with the user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites = new HashSet<>();

    // Getters and setters...
    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }
}
 */
}