package com.inspark.sabeel.config;

import com.inspark.sabeel.common.ApplicationAuditAware;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.UUID;

/**
 * BeansConfig is a configuration class that defines various beans for the application.
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfig {

    @Value("${application.cors.allowed-origins:*}")
    private List<String> allowedOrigins;

    private final UserDetailsService userDetailsService;

    /**
     * Defines a bean for AuditorAware to provide the current auditor.
     * This is used for auditing purposes, such as tracking who created or modified an entity.
     *
     * @return an instance of ApplicationAuditAware
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * Defines a bean for BCryptPasswordEncoder to encode passwords.
     * This is used to securely hash passwords before storing them.
     *
     * @return an instance of BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines a bean for CorsFilter to handle CORS (Cross-Origin Resource Sharing) configuration.
     * This allows the application to accept requests from different origins.
     *
     * @return an instance of CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (cookies, authorization headers, etc.) to be included in requests
        config.setAllowCredentials(true);

        // Set the allowed origins for CORS requests
        config.setAllowedOrigins(allowedOrigins);

        // Allow all headers in CORS requests
        config.setAllowedHeaders(List.of("*"));

        // Allow all HTTP methods in CORS requests
        config.setAllowedMethods(List.of("*"));

        // Register the CORS configuration for all paths
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Defines a bean for AuthenticationProvider to handle authentication.
     * This is used to authenticate users based on their credentials.
     *
     * @return an instance of DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Defines a bean for AuthenticationManager to manage authentication.
     * This will be used to authenticate users during the authentication process.
     *
     * @param config the authentication configuration
     * @return an instance of AuthenticationManager
     * @throws Exception if an error occurs during authentication manager creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}