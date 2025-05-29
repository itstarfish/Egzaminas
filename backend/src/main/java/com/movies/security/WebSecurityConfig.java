package com.movies.security;

import com.movies.security.jwt.AuthEntryPointJwt;
import com.movies.security.jwt.AuthTokenFilter;
import com.movies.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Web security configuration class for the application.
 * Configures authentication, authorization, and security filters.
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    // Service to handle user-related operations for Spring Security.
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // Component responsible for handling unauthorized access attempts.
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * Bean definition for the custom authentication JWT token filter.
     *
     * @return AuthTokenFilter instance
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Bean definition for the DAO authentication provider.
     * Configures the userDetailsService and password encoder.
     *
     * @return DaoAuthenticationProvider instance
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Set the custom user details service and password encoder.
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Bean definition for the authentication manager.
     *
     * @param authConfig the authentication configuration
     * @return AuthenticationManager instance
     * @throws Exception if an error occurs during AuthenticationManager creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean definition for the password encoder.
     * Uses BCryptPasswordEncoder to securely encode passwords.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for the security filter chain.
     * Configures HTTP security, session management, exception handling, and authentication filters.
     *
     * @param http the HttpSecurity object
     * @return SecurityFilterChain instance
     * @throws Exception if an error occurs during HttpSecurity configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                        .configurationSource(request -> {
                            var configuration = new org.springframework.web.cors.CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Vite dev server
                            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            configuration.setAllowedHeaders(List.of("*"));
                            configuration.setAllowCredentials(true);
                            return configuration;
                        })
                )
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/movies/**").permitAll()
                        .requestMatchers("/api/reviews/**").permitAll()
                        .anyRequest().permitAll()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}