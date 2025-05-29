package com.movies.service;

import com.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing movies.
 */
public interface MoviesService {


    Page<Movie> findAll(Pageable pageable);


    Page<Movie> findByNameContaining(String name, Pageable pageable);


    Page<Movie> findByMovieCategory(Long id, Pageable pageable);


    Movie findById(long Id);


    Movie save(Movie movie);


    void deleteById(int theId);
}
