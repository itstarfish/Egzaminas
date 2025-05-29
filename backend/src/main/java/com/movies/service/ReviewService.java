package com.movies.service;

import com.movies.entity.Review;

import java.util.List;

/**
 * Service interface for managing reviews.
 */
public interface ReviewService {


    List<Review> findAll();

    List<Review> findByMovieId(Long movieId);

    Review save(Review review);

    Review findById(Long reviewId);

    void deleteById(Long reviewId);
}
