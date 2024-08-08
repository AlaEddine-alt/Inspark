package com.inspark.sabeel.auth.infrastructure.dto.request;

public record SignInDto(
        String email,
        String password
) {
}
