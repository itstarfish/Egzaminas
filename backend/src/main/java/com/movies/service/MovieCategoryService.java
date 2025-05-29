package com.movies.service;

import com.movies.entity.MovieCategory;
import com.movies.entity.User;

import java.util.List;

/**
 * Service interface for managing movie categories.
 */
public interface MovieCategoryService {

    List<MovieCategory> findAll();

    MovieCategory findById(Long id);

    MovieCategory save(MovieCategory movieCategory);

    void deleteById(Long id);

    boolean existsByName(String name);
}
