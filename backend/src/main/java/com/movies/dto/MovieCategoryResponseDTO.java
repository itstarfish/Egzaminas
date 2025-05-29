package com.movies.dto;

import java.time.LocalDateTime;

/**
 * A DTO representing the response for a Movie Category.
 */
public class MovieCategoryResponseDTO {

    // Unique identifier for the movie category
    private Long id;

    // Name of the movie category
    private String name;

    // ID of the user who created the movie category
    private Long createdBy;

    // Timestamp of when the movie category was created
    private LocalDateTime createdAt;

    // Timestamp of the last update to the movie category
    private LocalDateTime updatedAt;

    /**
     * Gets the unique identifier for the movie category.
     *
     * @return the ID of the movie category
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the movie category.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the movie category.
     *
     * @return the name of the movie category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the movie category.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the user who created the movie category.
     *
     * @return the ID of the creator
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the ID of the user who created the movie category.
     *
     * @param createdBy the ID of the creator to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the timestamp of when the movie category was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp of when the movie category was created.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp of the last update to the movie category.
     *
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp of the last update to the movie category.
     *
     * @param updatedAt the last update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

