package com.movies.dao;

import com.movies.entity.Role;
import com.movies.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a Role entity by its name.
     *
     * @param name the name of the role (of type Roles)
     * @return an Optional containing the Role if found, otherwise empty
     */
    Optional<Role> findByName(Roles name);
}
