package com.movies.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a Review.
 */
@Entity
@Table(name = "reviews")
public class Review {

    // Primary key for the Review entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Text of the review
    @Column(name = "review")
    private String review;

    // Rating given in the review
    @Column(name = "rating")
    private Integer rating;

    // User who created the review
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;


    // Timestamp when the review was created
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Timestamp when the review was last updated
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Movie to which the review is related
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    /**
     * Default no-arg constructor for Review.
     */
    public Review() {
    }

    /**
     * Constructor to initialize all fields of the Review entity.
     *
     * @param id        ID of the review
     * @param review    Text of the review
     * @param rating    Rating given in the review
     * @param createdBy User who created the review
     * @param createdAt Timestamp of review creation
     * @param updatedAt Timestamp of the last update of the review
     * @param movie     The movie associated with the review
     */
    public Review(Long id, String review, Integer rating, User createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, Movie movie) {
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.movie = movie;
    }

    // Getter for the review ID
    public Long getId() {
        return id;
    }

    // Getter for the review text
    public String getReview() {
        return review;
    }

    // Setter for the review text
    public void setReview(String review) {
        this.review = review;
    }

    // Getter for the rating
    public Integer getRating() {
        return rating;
    }

    // Setter for the rating
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    // Getter for the created timestamp
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Getter for the associated movie
    public Movie getMovie() {
        return movie;
    }

    // Setter for the associated movie
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    // Setter for the created timestamp
    public void setCreatedAt(LocalDateTime now) {
        // Intentionally left empty
    }

    // Setter for the review ID
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for the user who created the review
    public User getCreatedBy() {
        return createdBy;
    }

    // Setter for the user who created the review
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    // Getter for the last updated timestamp
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setter for the last updated timestamp
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
