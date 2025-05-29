package com.movies.controllers;

import com.movies.dto.ReviewCreateDTO;
import com.movies.dto.ReviewResponseDTO;
import com.movies.dto.ReviewUpdateDTO;
import com.movies.entity.Movie;
import com.movies.entity.Review;
import com.movies.entity.User;
import com.movies.mapper.ReviewMapper;
import com.movies.service.UserDetailsImpl;
import com.movies.service.MoviesService;
import com.movies.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReviewsController handles CRUD operations for movie reviews.
 */
@RestController
@RequestMapping("/api/v1/")
public class ReviewsController {

    private final ReviewService reviewService;
    private final MoviesService moviesService;

    /**
     * Constructor to inject required services.
     *
     * @param reviewService The review service for CRUD operations on reviews.
     * @param moviesService The movie service for retrieving movie data.
     */
    @Autowired
    public ReviewsController(ReviewService reviewService, MoviesService moviesService) {
        this.reviewService = reviewService;
        this.moviesService = moviesService;
    }

    /**
     * Retrieves all reviews.
     *
     * @return A list of ReviewResponseDTO wrapped in a ResponseEntity.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Restrict access to users with specific roles
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves reviews for a specific movie.
     *
     * @param movieId The ID of the movie.
     * @return A list of ReviewResponseDTO for the specified movie.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Restrict access to users with specific roles
    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByMovieId(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.findByMovieId(movieId);
        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a new review for a movie.
     *
     * @param reviewDTO   The DTO containing the review data.
     * @param currentUser The authenticated current user creating the review.
     * @return The created ReviewResponseDTO.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Restrict access to users with specific roles
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@Valid @RequestBody ReviewCreateDTO reviewDTO,
                                                       @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Movie movie = moviesService.findById(reviewDTO.getMovieId());
        User createdBy = new User();
        createdBy.setId(currentUser.getId());
        createdBy.setUsername(currentUser.getUsername());
        createdBy.setEmail(currentUser.getEmail());

        Review review = ReviewMapper.toEntity(reviewDTO, movie, createdBy);
        review.setCreatedAt(LocalDateTime.now());
        Review saved = reviewService.save(review);
        return ResponseEntity.ok(ReviewMapper.toResponse(saved));
    }

    /**
     * Updates an existing review.
     *
     * @param reviewId    The ID of the review to be updated.
     * @param reviewDTO   The DTO containing updated review data.
     * @param currentUser The authenticated current user attempting to update the review.
     * @return The updated ReviewResponseDTO.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Restrict access to users with specific roles
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewUpdateDTO reviewDTO,
                                                          @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Review existing = reviewService.findById(reviewId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        existing.setReview(reviewDTO.getReview());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setCreatedBy(existing.getCreatedBy());

        Review saved = reviewService.save(existing);
        return ResponseEntity.ok(ReviewMapper.toResponse(saved));
    }

    /**
     * Deletes a review.
     *
     * @param reviewId    The ID of the review to be deleted.
     * @param currentUser The authenticated current user attempting to delete the review.
     * @return A message indicating successful deletion.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Restrict access to users with specific roles
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId,
                                               @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Review existing = reviewService.findById(reviewId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        reviewService.deleteById(reviewId);
        return ResponseEntity.ok("Deleted review id - " + reviewId);
    }
}
