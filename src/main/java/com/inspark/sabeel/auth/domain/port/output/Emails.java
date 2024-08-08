package com.inspark.sabeel.auth.domain.port.output;

import com.inspark.sabeel.auth.domain.enums.EmailType;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface Emails {
    @Async
    void sendEmail(
            String to,
            EmailType emailType,
            String username,
            String code
    ) throws MessagingException;
}
