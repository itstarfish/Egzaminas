package com.movies.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movie_categories")
public class MovieCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Unique identifier for the movie category

    @Column(name = "name")
    private String name; // Name of the movie category

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy; // User who created the movie category

    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    ; // Timestamp when the movie category was created

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt; // Timestamp when the movie category was last updated

    // Default no-argument constructor
    public MovieCategory() {
    }

    // Parameterized constructor for initializing the movie category
    public MovieCategory(Long id, String name, User createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for createdBy
    public User getCreatedBy() {
        return createdBy;
    }

    // Setter for createdBy
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    // Getter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setter for createdAt
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter for updatedAt
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setter for updatedAt
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
