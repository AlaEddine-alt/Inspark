package com.inspark.sabeel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig is a configuration class that sets up security configurations for the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${application.base-url}")
    private String securedUrl;

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain for JWT authorization.
     * This method is called by Spring Security to configure the security filter chain.
     * It sets up CORS, CSRF, session management, and authentication provider configurations.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain jwtAuthorizationSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with default settings
                .cors(Customizer.withDefaults())

                // Disable CSRF protection as JWT is used
                .csrf(AbstractHttpConfigurer::disable)

                // Configure session management to be stateless as JWT doesn't need sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure authorization rules
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        securedUrl
                                ).authenticated() // Secure the specified URL
                                .anyRequest().permitAll() // Allow all other requests
                )

                // Set the authentication provider
                .authenticationProvider(authenticationProvider)

                // Add the JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}