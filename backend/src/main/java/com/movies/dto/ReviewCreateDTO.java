package com.movies.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ReviewCreateDTO {

    // The content of the review; cannot be empty
    @NotBlank(message = "Review cannot be empty!")
    private String review;

    // The rating for the movie; must be between 1 and 10
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must be at most 10")
    private Integer rating;

    // The ID of the movie being reviewed; cannot be null
    @NotNull(message = "Movie ID is required")
    private Long movieId;

    // Getter for the review content
    public String getReview() {
        return review;
    }

    // Setter for the review content
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

    // Getter for the movie ID
    public Long getMovieId() {
        return movieId;
    }

    // Setter for the movie ID
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

}
