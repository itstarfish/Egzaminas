package com.movies.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Movie.
 */
@Entity
@Table(name = "movies")
public class Movie {

    // Primary key field for the Movie entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Name of the movie
    @Column(name = "name")
    private String name;

    // Description of the movie
    @Column(name = "description")
    private String description;

    // IMDB rating of the movie
    @Column(name = "IMDBrating")
    private Double IMDBrating;

    // Identifier for the category of the movie
    @Column(name = "movie_category_id")
    private Long movieCategory;

    // User who created the movie entry
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // Timestamp when the movie entry was created
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Timestamp when the movie entry was last updated
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    /**
     * Default no-arg constructor for Movie.
     */
    public Movie() {

    }

    /**
     * Constructor for creating a Movie object with all fields.
     *
     * @param id            The ID of the movie
     * @param name          The name of the movie
     * @param description   A brief description of the movie
     * @param IMDBrating    The IMDB rating of the movie
     * @param movieCategory The category ID of the movie
     * @param createdBy     The user who created the movie entry
     * @param createdAt     The creation timestamp of the movie entry
     * @param updatedAt     The last update timestamp of the movie entry
     */
    public Movie(Long id, String name, String description, Double IMDBrating, Long movieCategory, User createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.IMDBrating = IMDBrating;
        this.movieCategory = movieCategory;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getIMDBrating() {
        return IMDBrating;
    }

    public void setIMDBrating(Double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(Long movieCategory) {
        this.movieCategory = movieCategory;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    /* @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favoritedBy = new HashSet<>();

    // Getters and setters...
    public Set<Favorite> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(Set<Favorite> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }
*/
}