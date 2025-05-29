package com.movies.dto;

import jakarta.validation.constraints.*;

public class ReviewUpdateDTO {

    // Review text; cannot be empty
    @NotBlank(message = "Review cannot be empty!")
    private String review;

    // Rating for the review; must be between 1 and 10 (inclusive)
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must be at most 10")
    private Integer rating;

    // Getter for the review text
    public String getReview() {
        return review;
    }

    // Setter for the review text
    public void setReview(String review) {
        this.review = review;
    }

    // Getter for the rating value
    public Integer getRating() {
        return rating;
    }

    // Setter for the rating value
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}