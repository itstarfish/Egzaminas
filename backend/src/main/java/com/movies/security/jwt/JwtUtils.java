package com.movies.security.jwt;

import java.security.Key;
import java.util.Date;

import com.movies.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("======================EGZ=Spring===========================") // Inject the secret key for JWT from application properties
    private String jwtSecret;

    @Value("86400000") // Inject the expiration time for JWT from application properties
    private int jwtExpirationMs;

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication The authentication object containing authenticated user details.
     * @return A JWT token as a string.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Set the username as the subject
                .setIssuedAt(new Date()) // Set the current date as the issue date
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set the expiration date
                .signWith(key(), SignatureAlgorithm.HS256) // Sign the JWT with the secret key using HS256 algorithm
                .compact();
    }

    /**
     * Decodes the secret key stored as a Base64 string.
     *
     * @return The decoded secret key as a Key object.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Decode the Base64-encoded key
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username (subject) stored inside the JWT token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build() // Build the JWT parser with the signing key
                .parseClaimsJws(token).getBody().getSubject(); // Parse the token and get the subject
    }

    /**
     * Validates the JWT token to ensure it's properly signed and not expired.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, otherwise false.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken); // Validate the token
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage()); // Log error for invalid token
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage()); // Log error for expired token
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage()); // Log error for unsupported token
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage()); // Log error for empty JWT claims
        }
        return false; // Return false if any exception is thrown
    }
}
