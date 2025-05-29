package com.movies.controllers;

import com.movies.dto.MovieCategoryCreateDTO;
import com.movies.dto.MovieCategoryResponseDTO;
import com.movies.entity.MovieCategory;
import com.movies.entity.User;
import com.movies.service.UserDetailsImpl;
import com.movies.service.MovieCategoryService;
import com.movies.mapper.MovieCategoryMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/categories")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class MovieCategoriesController {

    private final MovieCategoryService movieCategoryService;

    @Autowired
    public MovieCategoriesController(MovieCategoryService movieCategoryService) {
        this.movieCategoryService = movieCategoryService;
    }

    /**
     * Retrieves all movie categories.
     *
     * @return a list of movie categories wrapped in a ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<MovieCategoryResponseDTO>> findAll() {
        List<MovieCategory> categories = movieCategoryService.findAll();
        List<MovieCategoryResponseDTO> response = categories.stream()
                .map(MovieCategoryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a specific movie category by ID.
     *
     * @param categoryId the ID of the category to retrieve
     * @return the movie category wrapped in a ResponseEntity, or 404 if not found
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<MovieCategoryResponseDTO> getCategory(@PathVariable Long categoryId) {
        MovieCategory category = movieCategoryService.findById(categoryId);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(category));
    }

    /**
     * Creates a new movie category.
     *
     * @param categoryDTO the DTO object containing category details
     * @param currentUser the currently authenticated user
     * @return the created movie category wrapped in a ResponseEntity
     */
    @PostMapping
    public ResponseEntity<MovieCategoryResponseDTO> addCategory(
            @Valid @RequestBody MovieCategoryCreateDTO categoryDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        if (movieCategoryService.existsByName(categoryDTO.getName())) {
            return ResponseEntity.badRequest().body(null);
        }

        // Set the creator information from the current user
        User createdBy = new User();
        createdBy.setId(currentUser.getId());
        createdBy.setUsername(currentUser.getUsername());
        createdBy.setEmail(currentUser.getEmail());

        // Map the DTO to an entity and save
        MovieCategory newCategory = MovieCategoryMapper.toEntity(categoryDTO, createdBy);
        newCategory.setCreatedAt(LocalDateTime.now());
        MovieCategory savedCategory = movieCategoryService.save(newCategory);
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(savedCategory));
    }

    /**
     * Updates an existing movie category.
     *
     * @param categoryId  the ID of the category to update
     * @param categoryDTO the DTO object containing updated category details
     * @param currentUser the currently authenticated user
     * @return the updated movie category wrapped in a ResponseEntity
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<MovieCategoryResponseDTO> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody MovieCategoryCreateDTO categoryDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        MovieCategory existing = movieCategoryService.findById(categoryId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the current user is the creator of the category
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        // Map the DTO to an entity and update fields
        User createdBy = existing.getCreatedBy();
        MovieCategory updatedCategory = MovieCategoryMapper.toEntity(categoryDTO, createdBy);
        updatedCategory.setId(categoryId);
        updatedCategory.setCreatedAt(existing.getCreatedAt());
        updatedCategory.setUpdatedAt(LocalDateTime.now());

        MovieCategory savedCategory = movieCategoryService.save(updatedCategory);
        return ResponseEntity.ok(MovieCategoryMapper.toResponse(savedCategory));
    }

    /**
     * Deletes a specific movie category by ID.
     *
     * @param categoryId  the ID of the category to delete
     * @param currentUser the currently authenticated user
     * @return a success message wrapped in a ResponseEntity, or 403 if unauthorized
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId,
                                                 @AuthenticationPrincipal UserDetailsImpl currentUser) {
        MovieCategory existing = movieCategoryService.findById(categoryId);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the current user is the creator of the category
        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body("You are not authorized to delete this category");
        }

        // Delete the category and return a response
        movieCategoryService.deleteById(categoryId);
        return ResponseEntity.ok("Deleted movie category id - " + categoryId);
    }
}
