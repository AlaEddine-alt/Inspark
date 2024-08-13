package com.inspark.sabeel.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Oauth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Value("${application.oauth2.callback-failure-url}")
    private String failureUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("Authentication failed: {}", exception.getMessage());
        response.sendRedirect(failureUrl);
    }
}
