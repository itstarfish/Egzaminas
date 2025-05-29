package com.movies.service;

import com.movies.dao.MovieCategoryRepository;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the MovieCategoryService interface for managing movie categories.
 */
@Service
public class MovieCategoryServiceImpl implements MovieCategoryService {

    // Repository for managing MovieCategory data
    private final MovieCategoryRepository movieCategoryRepository;

    @Autowired
    public MovieCategoryServiceImpl(MovieCategoryRepository movieCategoryRepository) {
        this.movieCategoryRepository = movieCategoryRepository;
    }

    @Override
    public List<MovieCategory> findAll() {
        return movieCategoryRepository.findAll();
    }

    @Override
    public MovieCategory findById(Long id) {
        return movieCategoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Did not find movie category id - " + id));
    }

    @Override
    public MovieCategory save(MovieCategory movieCategory) {
        return movieCategoryRepository.save(movieCategory);
    }

    @Override
    public void deleteById(Long id) {
        movieCategoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return movieCategoryRepository.findByName(name) != null;
    }


}
