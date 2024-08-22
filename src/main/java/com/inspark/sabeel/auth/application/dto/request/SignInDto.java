package com.inspark.sabeel.auth.application.dto.request;

public record SignInDto(
        String email,
        String password
) {
}
