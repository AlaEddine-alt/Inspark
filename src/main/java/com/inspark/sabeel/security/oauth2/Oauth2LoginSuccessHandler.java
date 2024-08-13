package com.inspark.sabeel.security.oauth2;

import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import com.inspark.sabeel.auth.infrastructure.repository.RolesRepository;
import com.inspark.sabeel.auth.infrastructure.utils.TokenUtils;
import com.inspark.sabeel.security.JwtService;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final JwtService jwtService;
    private final TokenUtils tokenUtils;

    @Value("${application.oauth2.callback-success-url}")
    private String callbackUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Authentication: {}", authentication);
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        AppUser appUser = AppUser.fromGoogleUser(oidcUser);
        UserEntity userEntity = userRepository.findByEmail(appUser.getEmail()).orElse(null);
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = authenticationToken.getAuthorizedClientRegistrationId();

        if (userEntity == null) {
            String googleId = appUser.getId(); // This is the 'sub' attribute from Google
            RoleEntity userRole = roleRepository.findByName("USER");
            var userToSave = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .provider(provider)
                    .providerId(googleId)
                    .firstName(appUser.getGivenName())
                    .lastName(appUser.getFamilyName())
                    .emailVerified(true)
                    .enabled(true)
                    .accountLocked(false)
                    .roles(Set.of(userRole))
                    .version(0)
                    .email(appUser.getEmail())
                    .build();
            userEntity = userRepository.save(userToSave);  // Save the user and assign to userEntity
        }

        // Generate JWT token and refresh token
        var claims = new HashMap<String, Object>();
        claims.put("fullName", userEntity.getFullName());
        claims.put("email", userEntity.getEmail());
        String token = jwtService.generateToken(claims, userEntity);
        String refreshToken = jwtService.generateRefreshToken(claims, userEntity);

        // Revoke existing tokens and save the new token
        tokenUtils.revokeAllUserTokens(userEntity.getId());
        tokenUtils.saveUserToken(userEntity.getId(), token);

        // Set the authentication in the context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String redirectUrl = callbackUrl;
        redirectUrl += "?accessToken=" + token;
        redirectUrl += "&refreshToken=" + refreshToken;
        response.sendRedirect(redirectUrl);
    }


}
