package com.movies.mapper;

import com.movies.dto.MovieCreateDTO;
import com.movies.dto.MovieResponseDTO;
import com.movies.entity.Movie;
import com.movies.entity.User;

import java.time.LocalDateTime;

public class MovieMapper {

    /**
     * Maps a MovieCreateDTO object and the User who created it to a Movie entity.
     *
     * @param dto       the DTO containing movie creation data
     * @param createdBy the user who created the movie
     * @return a Movie entity populated with the provided DTO and user information
     */
    public static Movie toEntity(MovieCreateDTO dto, User createdBy) {
        Movie movie = new Movie();
        movie.setName(dto.getName());
        movie.setDescription(dto.getDescription());
        movie.setIMDBrating(dto.getIMDBrating());
        movie.setMovieCategory(dto.getMovieCategory());
        movie.setCreatedBy(createdBy);
        return movie;
    }

    /**
     * Maps a Movie entity to a MovieResponseDTO object.
     *
     * @param entity the Movie entity to be mapped
     * @return a MovieResponseDTO object containing the entity's data
     */
    public static MovieResponseDTO toResponse(Movie entity) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIMDBrating(entity.getIMDBrating());
        dto.setMovieCategory(entity.getMovieCategory());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        return dto;
    }
}
