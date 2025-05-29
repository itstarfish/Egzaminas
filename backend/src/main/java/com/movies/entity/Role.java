package com.movies.entity;

import com.movies.enums.Roles;
import jakarta.persistence.*;

/**
 * Entity class representing a Role.
 */
@Entity
@Table(name = "roles")
public class Role {

    // Primary key field for the Role entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Name of the role, stored as an enumerated type
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    /**
     * Default no-arg constructor for Role.
     */
    public Role() {

    }

    /**
     * Constructor for creating a Role object.
     *
     * @param name The name of the role
     */
    public Role(Roles name) {
        this.name = name;
    }

    /**
     * Gets the ID of the role.
     *
     * @return The ID of the role
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the role.
     *
     * @param id The new ID of the role
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     *
     * @return The name of the role
     */
    public Roles getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name The new name of the role
     */
    public void setName(Roles name) {
        this.name = name;
    }
}