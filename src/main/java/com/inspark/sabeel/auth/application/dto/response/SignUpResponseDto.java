package com.inspark.sabeel.auth.application.dto.response;

import java.util.Set;

public record SignUpResponseDto(
        String id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Set<String> roles
) {
}
