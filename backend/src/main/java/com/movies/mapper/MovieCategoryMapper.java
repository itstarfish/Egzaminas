package com.movies.mapper;

import com.movies.dto.MovieCategoryCreateDTO;
import com.movies.dto.MovieCategoryResponseDTO;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;

import java.time.LocalDateTime;

public class MovieCategoryMapper {

    /**
     * Converts a MovieCategoryCreateDTO to a MovieCategory entity.
     *
     * @param dto       the DTO containing the creation details of the movie category
     * @param createdBy the user who created the movie category
     * @return a new MovieCategory entity
     */
    public static MovieCategory toEntity(MovieCategoryCreateDTO dto, User createdBy) {
        MovieCategory category = new MovieCategory();
        category.setName(dto.getName());
        category.setCreatedBy(createdBy);
        return category;
    }

    /**
     * Converts a MovieCategory entity to a MovieCategoryResponseDTO.
     *
     * @param entity the MovieCategory entity to convert
     * @return the corresponding MovieCategoryResponseDTO
     */
    public static MovieCategoryResponseDTO toResponse(MovieCategory entity) {
        MovieCategoryResponseDTO dto = new MovieCategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
