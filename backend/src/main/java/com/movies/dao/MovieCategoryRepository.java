package com.movies.dao;

import com.movies.entity.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing MovieCategory entities.
 * Provides methods for performing CRUD operations and custom queries on MovieCategory data.
 */
public interface MovieCategoryRepository extends JpaRepository<MovieCategory, Long> {

    /**
     * Finds a MovieCategory entity by its name.
     *
     * @param name the name of the MovieCategory
     * @return the MovieCategory entity with the specified name, or null if not found
     */
    MovieCategory findByName(String name);
}
