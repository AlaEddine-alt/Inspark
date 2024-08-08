package com.inspark.sabeel.user.infrastructure.dto;

import java.util.Set;

public record UserDto(
        String id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Set<String> roles
) {
}
