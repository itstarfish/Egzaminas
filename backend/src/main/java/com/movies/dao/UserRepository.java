package com.movies.dao;

import com.movies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by its username.
     *
     * @param username the username of the user
     * @return an Optional containing the User if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a User entity by its id.
     *
     * @param id the id of the user
     * @return an Optional containing the User if found, otherwise empty
     */
    Optional<User> findById(Long id);

    /**
     * Checks if a User with the given username exists.
     *
     * @param username the username to check
     * @return true if a User with the given username exists, otherwise false
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a User with the given email exists.
     *
     * @param email the email to check
     * @return true if a User with the given email exists, otherwise false
     */
    Boolean existsByEmail(String email);
}