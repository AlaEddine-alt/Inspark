package com.inspark.sabeel.auth.application.dto.request;

public record ResetPasswordDto(
        String code,
        String password,
        String confirmPassword
) {
}
