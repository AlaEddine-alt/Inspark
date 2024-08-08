package com.inspark.sabeel.auth.infrastructure.adapter.email;

import com.inspark.sabeel.auth.domain.enums.EmailType;
import com.inspark.sabeel.auth.domain.port.output.Emails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailAdapter implements Emails {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    
    @Async
    @Override
    public void sendEmail(
            String to,
            EmailType emailType,
            String username,
            String code
    ) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        helper.setSubject(emailType.getSubject());
        helper.setFrom("oussemasahbeni300@gmail.com");
        helper.setTo(to);
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", emailType.getConfirmationUrl());
        properties.put("code", code);
        Context context = new Context();
        context.setVariables(properties);
        String template = templateEngine.process(emailType.getTemplateName(), context);
        helper.setText(template, true);
        emailSender.send(message);

    }

}
