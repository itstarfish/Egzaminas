package com.movies.dto;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    // Review ID
    private Long id;

    // Review content
    private String review;

    // Rating given in the review
    private Integer rating;

    // Timestamp when the review was created
    private LocalDateTime createdAt;

    // Timestamp when the review was last updated
    private LocalDateTime updatedAt;

    // ID of the associated movie
    private Long movieId;

    // ID of the user who created the review
    private Long createdBy;

    // Name of the user who created the review
    private String createdByName;

    /**
     * Gets the ID of the review.
     *
     * @return the review ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the review.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the content of the review.
     *
     * @return the review content
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the content of the review.
     *
     * @param review the review content to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets the rating of the review.
     *
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating of the review.
     *
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gets the creation timestamp of the review.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the review.
     *
     * @param createdAt the timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the ID of the associated movie.
     *
     * @return the associated movie ID
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * Sets the ID of the associated movie.
     *
     * @param movieId the movie ID to set
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    /**
     * Gets the ID of the user who created the review.
     *
     * @return the user ID
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the ID of the user who created the review.
     *
     * @param createdBy the user ID to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last updated timestamp of the review.
     *
     * @return the last updated timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last updated timestamp of the review.
     *
     * @param updatedAt the timestamp to set
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
