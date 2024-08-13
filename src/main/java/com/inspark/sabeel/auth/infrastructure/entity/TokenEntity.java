package com.inspark.sabeel.auth.infrastructure.entity;


import com.inspark.sabeel.auth.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens", indexes = {
        @Index(name = "idx_token", columnList = "token", unique = true),
        @Index(name = "idx_user_id", columnList = "userId")
})
public class TokenEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, length = 500)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    private UUID userId;
}
