package com.inspark.sabeel.auth.infrastructure.dto.response;

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
