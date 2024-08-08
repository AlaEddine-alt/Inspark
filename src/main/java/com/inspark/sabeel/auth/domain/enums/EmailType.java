package com.inspark.sabeel.auth.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    ACCOUNT_CONFIRMATION(
            "Account Confirmation",
            "account-confirmation",
            "http://localhost:4200/account-confirmation"
    ),
    RESET_PASSWORD(
            "Reset Password",
            "reset-password.html",
            "http://localhost:4200/reset-password"
    );

    private final String subject;
    private final String templateName;
    private final String confirmationUrl;

}
