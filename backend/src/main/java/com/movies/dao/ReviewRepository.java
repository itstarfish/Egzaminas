package com.movies.dao;

import com.movies.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Review entities.
 * Provides methods for performing CRUD operations and custom queries on Review data.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds a list of reviews by the given movie ID.
     *
     * @param movieId the ID of the movie
     * @return a list of reviews associated with the specified movie ID
     */
    List<Review> findByMovieId(Long movieId);
}
