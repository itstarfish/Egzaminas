package com.movies.service;

import com.movies.dao.MoviesRepository;
import com.movies.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the MoviesService interface for managing movies.
 * Provides business logic and interacts with the MoviesRepository.
 */
@Service
public class MoviesServiceImpl implements MoviesService {

    // Repository for performing CRUD operations on Movie entities
    private MoviesRepository moviesRepository;


    @Autowired
    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return moviesRepository.findAll(pageable);
    }


    @Override
    public Movie findById(long Id) {
        Optional<Movie> result = moviesRepository.findById((int) Id);

        Movie movie = null;

        if (result.isPresent()) {
            movie = result.get();
        } else {
            throw new RuntimeException("Did not find movie id - " + Id);
        }

        return movie;
    }

    @Override
    public Movie save(Movie movie) {
        return moviesRepository.save(movie);
    }


    @Override
    public void deleteById(int id) {
        moviesRepository.deleteById(id);
    }


    public Page<Movie> findByNameContaining(String name, Pageable pageable) {
        return moviesRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Movie> findByMovieCategory(Long id, Pageable pageable) {
        return moviesRepository.findByMovieCategory(id, pageable);
    }
}