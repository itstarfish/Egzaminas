package com.movies.dao;

import com.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on the Movie entity.
 */
public interface MoviesRepository extends JpaRepository<Movie, Integer> {

    /**
     * Retrieves a paginated list of movies whose names contain the given search text, ignoring case.
     *
     * @param name     The search text to look for in movie names.
     * @param pageable The pageable object containing pagination information.
     * @return A page of movies that match the search criteria.
     */
    Page<Movie> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Retrieves a paginated list of movies whose category name contain the given search text, ignoring case.
     *
     * @param id     The search text to look for in movie names.
     * @param pageable The pageable object containing pagination information.
     * @return A page of movies that match the search criteria.
     */
    Page<Movie> findByMovieCategory(Long id, Pageable pageable);
}
