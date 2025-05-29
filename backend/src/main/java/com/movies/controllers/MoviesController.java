package com.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.config.PaginationProperties;
import com.movies.dao.MoviesRepository;
import com.movies.dto.MovieCreateDTO;
import com.movies.dto.MovieResponseDTO;
import com.movies.entity.Movie;
import com.movies.entity.User;
import com.movies.mapper.MovieMapper;
import com.movies.service.UserDetailsImpl;
import com.movies.service.MoviesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/movies")
@PreAuthorize("hasRole('USER')||hasRole('ADMIN')")
public class MoviesController {

    private final MoviesService moviesService;
    private final MoviesRepository moviesRepository;
    private final PaginationProperties paginationProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public MoviesController(
            MoviesService moviesService,
            MoviesRepository moviesRepository,
            PaginationProperties paginationProperties,
            ObjectMapper objectMapper
    ) {
        this.moviesService = moviesService;
        this.moviesRepository = moviesRepository;
        this.paginationProperties = paginationProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves a paginated and optionally searchable list of movies.
     *
     * @param page    the page number (default: 0)
     * @param size    the page size (default: 10)
     * @param sortBy  the field to sort by (default: "id")
     * @param sortDir the sort direction, either asc or desc (default: "asc")
     * @param search  an optional search term for filtering by movie name
     * @return a ResponseEntity containing a map with movie data
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir,
            @RequestParam(required = false) String search
    ) {
        int resolvedPage = (page == null) ? 0 : page;
        int resolvedSize = (size == null) ? 10 : size;

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(resolvedPage, resolvedSize, sort);

        Page<Movie> moviePage;

        if (search != null && !search.isEmpty()) {
            moviePage = moviesService.findByNameContaining(search, pageable);
        } else {
            moviePage = moviesService.findAll(pageable);
        }

        List<MovieResponseDTO> movies = moviePage.getContent().stream()
                .map(MovieMapper::toResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "status", "success",
                "results", moviePage.getTotalElements(),
                "data", movies
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves details of a single movie by its ID.
     *
     * @param movieId the ID of the movie
     * @return a ResponseEntity containing the movie details or a 404 status if not found
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponseDTO> getMovie(@PathVariable Long movieId) {
        Movie movie = moviesService.findById(movieId);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MovieMapper.toResponse(movie));
    }

    /**
     * Creates a new movie with details provided in the request body.
     *
     * @param movieDTO    the movie creation object
     * @param currentUser the authenticated user creating the movie
     * @return a ResponseEntity containing the created movie details
     */
    @PostMapping
    public ResponseEntity<MovieResponseDTO> addMovie(
            @Valid @RequestBody MovieCreateDTO movieDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        User createdBy = new User();
        createdBy.setId(currentUser.getId());
        createdBy.setUsername(currentUser.getUsername());
        createdBy.setEmail(currentUser.getEmail());

        Movie newMovie = MovieMapper.toEntity(movieDTO, createdBy);
        newMovie.setCreatedAt(LocalDateTime.now());
        newMovie.setCreatedBy(createdBy);

        Movie saved = moviesService.save(newMovie);
        return ResponseEntity.ok(MovieMapper.toResponse(saved));
    }

    /**
     * Updates an existing movie identified by its ID.
     *
     * @param movieId     the ID of the movie to update
     * @param movieDTO    the updated movie details
     * @param currentUser the authenticated user performing the update
     * @return a ResponseEntity containing the updated movie details
     */
    @PutMapping("/{movieId}")
    public ResponseEntity<MovieResponseDTO> updateMovie(
            @PathVariable Long movieId,
            @Valid @RequestBody MovieCreateDTO movieDTO,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        Movie existing = moviesService.findById(movieId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        Movie updatedMovie = MovieMapper.toEntity(movieDTO, existing.getCreatedBy());

        updatedMovie.setId(movieId);
        updatedMovie.setCreatedAt(existing.getCreatedAt());
        updatedMovie.setUpdatedAt(LocalDateTime.now());
        updatedMovie.setCreatedBy(existing.getCreatedBy());

        Movie saved = moviesService.save(updatedMovie);
        return ResponseEntity.ok(MovieMapper.toResponse(saved));
    }

    /**
     * Deletes a movie identified by its ID.
     *
     * @param movieId     the ID of the movie to delete
     * @param currentUser the authenticated user performing the deletion
     * @return a ResponseEntity containing a success message or an error status
     */
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId,
                                              @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Movie tempMovie = moviesService.findById(movieId);

        if (tempMovie == null) {
            return ResponseEntity.notFound().build();
        }

        if (!tempMovie.getCreatedBy().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        moviesService.deleteById(movieId.intValue());
        return ResponseEntity.ok("Deleted movie id - " + movieId);
    }

    /**
     * Searches for movies by their name and returns the results.
     *
     * @param name the name or part of the name to search for
     * @return a ResponseEntity containing a list of matching movies
     */
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDTO>> searchMoviesByName(@RequestParam String name) {
        List<Movie> movies = moviesService.findByNameContaining(name, Pageable.unpaged()).getContent();
        List<MovieResponseDTO> response = movies.stream()
                .map(MovieMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Searches for movies by their category and returns the results.
     *
     * @param id the category or part of the category to search for
     * @return a ResponseEntity containing a list of matching movies
     */
    @GetMapping("/category")
    public ResponseEntity<List<MovieResponseDTO>> searchMoviesByCategory(@RequestParam Long id) {
        List<Movie> movies = moviesService.findByMovieCategory(id, Pageable.unpaged()).getContent();
        List<MovieResponseDTO> response = movies.stream()
                .map(MovieMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
