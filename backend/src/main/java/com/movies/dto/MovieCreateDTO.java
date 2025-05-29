package com.movies.dto;

import jakarta.validation.constraints.*;

public class MovieCreateDTO {

    // Movie name; cannot be blank
    @NotBlank(message = "A movie must have a name")
    private String name;

    // Optional movie description; maximum length is 255 characters
    @Size(max = 255, message = "Description must be 255 characters or less")
    private String description;

    // IMDB rating of the movie; default is 4.5, range is [0, 10]
    @DecimalMin(value = "0", message = "Ranking must be min 0")
    @DecimalMax(value = "10", message = "Ranking must be max 10")
    private Double IMDBrating = 4.5; // default

    // ID of the associated movie category; cannot be null
    @NotNull(message = "A movie must belong to a category")
    private Long movieCategory;

    // Getter for the movie name
    public String getName() {
        return name;
    }

    // Setter for the movie name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for the movie description
    public String getDescription() {
        return description;
    }

    // Setter for the movie description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for the IMDB rating of the movie
    public Double getIMDBrating() {
        return IMDBrating;
    }

    // Setter for the IMDB rating of the movie
    public void setIMDBrating(Double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    // Getter for the movie category ID
    public Long getMovieCategory() {
        return movieCategory;
    }

    // Setter for the movie category ID
    public void setMovieCategory(Long movieCategory) {
        this.movieCategory = movieCategory;
    }

}
