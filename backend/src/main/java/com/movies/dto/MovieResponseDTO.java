package com.movies.dto;

import java.time.LocalDateTime;

/**
 * A DTO class for handling movie response data.
 */
public class MovieResponseDTO {

    // The ID of the movie
    private Long id;

    // The name of the movie
    private String name;

    // The description of the movie
    private String description;

    // The IMDB rating of the movie
    private Double IMDBrating;

    // The ID of the movie category the movie belongs to
    private Long movieCategory;

    // The ID of the user who created the movie
    private Long createdBy;
    // The name of the user who create the movie
    private String createdByName;

    // The date and time when the movie was created
    private LocalDateTime createdAt;

    // The date and time when the movie was last updated
    private LocalDateTime updatedAt;

    /**
     * Gets the ID of the movie.
     *
     * @return the movie ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id the movie ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the movie.
     *
     * @return the movie name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the movie.
     *
     * @param name the movie name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the movie.
     *
     * @return the movie description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the movie.
     *
     * @param description the movie description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the IMDB rating of the movie.
     *
     * @return the IMDB rating
     */
    public Double getIMDBrating() {
        return IMDBrating;
    }

    /**
     * Sets the IMDB rating of the movie.
     *
     * @param IMDBrating the IMDB rating
     */
    public void setIMDBrating(Double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    /**
     * Gets the ID of the category this movie belongs to.
     *
     * @return the movie category ID
     */
    public Long getMovieCategory() {
        return movieCategory;
    }

    /**
     * Sets the ID of the category this movie belongs to.
     *
     * @param movieCategory the movie category ID
     */
    public void setMovieCategory(Long movieCategory) {
        this.movieCategory = movieCategory;
    }

    /**
     * Gets the ID of the user who created the movie.
     *
     * @return the created by user ID
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the ID of the user who created the movie.
     *
     * @param createdBy the created by user ID
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date and time when the movie was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the movie was created.
     *
     * @param createdAt the creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the movie was last updated.
     *
     * @return the last updated timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the movie was last updated.
     *
     * @param updatedAt the last updated timestamp
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}

