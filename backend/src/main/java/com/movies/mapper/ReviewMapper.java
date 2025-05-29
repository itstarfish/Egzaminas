package com.movies.mapper;

import com.movies.dto.ReviewResponseDTO;
import com.movies.dto.ReviewCreateDTO;
import com.movies.entity.Movie;
import com.movies.entity.Review;
import com.movies.entity.User;

import java.time.LocalDateTime;

public class ReviewMapper {

    /**
     * Maps a ReviewCreateDTO, Movie, and User into a Review entity.
     *
     * @param dto       the data transfer object containing review details
     * @param movie     the associated movie entity
     * @param createdBy the user who created the review
     * @return a Review entity populated with the provided data
     */
    public static Review toEntity(ReviewCreateDTO dto, Movie movie, User createdBy) {
        Review review = new Review();
        review.setReview(dto.getReview());
        review.setRating(dto.getRating());
        review.setMovie(movie);
        review.setCreatedBy(createdBy);
        return review;
    }

    /**
     * Maps a Review entity into a ReviewResponseDTO.
     *
     * @param entity the review entity to map
     * @return a ReviewResponseDTO populated with the review data
     */
    public static ReviewResponseDTO toResponse(Review entity) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(entity.getId());
        dto.setReview(entity.getReview());
        dto.setRating(entity.getRating());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setMovieId(entity.getMovie().getId());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        return dto;
    }
}
