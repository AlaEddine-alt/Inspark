package com.inspark.sabeel.auth.infrastructure.utils;

import com.inspark.sabeel.auth.domain.enums.TokenType;
import com.inspark.sabeel.auth.infrastructure.entity.TokenEntity;
import com.inspark.sabeel.auth.infrastructure.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenUtils {
    private final TokenRepository tokenRepository;

    public void saveUserToken(UUID userID, String jwtToken) {
        var token = TokenEntity.builder()
                .userId(userID)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
}
    public void revokeAllUserTokens(UUID userID) {
        var validUserTokens = tokenRepository.findAllNonExpiredOrNonRevokedByUserId(userID);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
