package com.movies.security.jwt;

import java.io.IOException;

import com.movies.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils; // Utility class for handling JWT operations.

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Service to load user details from the database.

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class); // Logger instance for logging events and errors.

    /**
     * Processes each request to extract and validate a JWT token, and sets the authentication in the
     * security context if the token is valid.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet exception occurs
     * @throws IOException      if an I/O exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Parse the JWT token from the request header
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Extract username from the token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Create an authentication token for the user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // No credentials are required
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Log an error if authentication cannot be set
            logger.error("Cannot set user authentication: {}", e);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Parses and extracts the JWT token from the 'Authorization' header of the request.
     *
     * @param request the HTTP servlet request
     * @return the JWT token if present, or null if not found
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization"); // Extract the 'Authorization' header.

        // Check if the header contains a Bearer token
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Return the token by removing the 'Bearer ' prefix
            return headerAuth.substring(7);
        }

        return null; // Return null if the token is not found
    }
}
