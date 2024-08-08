package com.inspark.sabeel.auth.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken {

    protected String token;
    protected long expiresIn;
    protected long refreshExpiresIn;
    protected String refreshToken;
}
