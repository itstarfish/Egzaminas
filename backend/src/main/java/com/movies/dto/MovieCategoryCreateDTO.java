package com.movies.dto;

import jakarta.validation.constraints.*;

/**
 * A DTO class for creating a new movie category.
 */
public class MovieCategoryCreateDTO {

    // The name of the movie category, cannot be blank
    @NotBlank(message = "A category must have a name")
    private String name;

    /**
     * Gets the name of the movie category.
     *
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the movie category.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
