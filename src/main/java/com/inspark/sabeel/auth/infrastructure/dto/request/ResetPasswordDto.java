package com.inspark.sabeel.auth.infrastructure.dto.request;

public record ResetPasswordDto(
        String code,
        String password,
        String confirmPassword
) {
}
