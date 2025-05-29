package com.movies.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.movies.dao.RoleRepository;
import com.movies.dao.UserRepository;
import com.movies.dto.JwtResponse;
import com.movies.dto.LoginRequest;
import com.movies.dto.MessageResponse;
import com.movies.dto.SignupRequest;
import com.movies.entity.Role;
import com.movies.entity.User;
import com.movies.enums.Roles;
import com.movies.security.jwt.JwtUtils;
import com.movies.service.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600) // Allow cross-origin requests
@RestController
@RequestMapping("/api/v1/auth") // Base path for authentication-related API endpoints
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager; // Dependency injection for managing authentication

    @Autowired
    UserRepository userRepository; // Dependency injection for user repository

    @Autowired
    RoleRepository roleRepository; // Dependency injection for role repository

    @Autowired
    PasswordEncoder encoder; // Dependency injection for password encoding

    @Autowired
    JwtUtils jwtUtils; // Dependency injection for JWT token utilities

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginRequest the login request containing username and password
     * @return ResponseEntity containing the JWT token and user details
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication in security context

        String jwt = jwtUtils.generateJwtToken(authentication); // Generate JWT token for the authenticated user

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); // Get user details from authentication
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()); // Extract user roles

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles)); // Return response with token and user details
    }

    /**
     * Registers a new user in the system.
     *
     * @param signUpRequest the sign-up request containing user details
     * @return ResponseEntity containing a success or error message
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check if email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())); // Encode the password

        Set<String> strRoles = null;
        Set<Role> roles = new HashSet<>();

        // Assign default roles if none are provided
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(Roles.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles); // Set roles to the new user
        userRepository.save(user); // Save the new user in the database

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Logs out the current user by clearing the SecurityContext.
     *
     * @return ResponseEntity with a success message
     */
    @PostMapping("/signout")
    public ResponseEntity<?> signout() {
        SecurityContextHolder.clearContext(); // Clear the SecurityContext to log out
        return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
    }
}