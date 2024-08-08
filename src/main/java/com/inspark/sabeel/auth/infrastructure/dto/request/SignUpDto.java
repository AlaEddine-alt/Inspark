package com.inspark.sabeel.auth.infrastructure.dto.request;

import com.inspark.sabeel.common.custom_validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record SignUpDto(
        @Email(message = "Invalid email address")
        @NotNull(message = "Email is required")
        @NotBlank(message = "Email is required")
        String email,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password is required")
        @ValidPassword
        String password,
        @NotNull(message = "First name is required")
        @NotBlank(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotNull(message = "Phone number is required")
        @NotBlank(message = "Phone number is required")
        @Min(value = 8, message = "Phone number must be exactly 8 digits long")
        String phoneNumber
) {
}

