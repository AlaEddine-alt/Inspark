package com.inspark.sabeel.auth.domain.service;


import com.inspark.sabeel.auth.domain.enums.CodeStatus;
import com.inspark.sabeel.auth.domain.enums.EmailType;
import com.inspark.sabeel.auth.domain.model.AccessToken;
import com.inspark.sabeel.auth.domain.model.Code;
import com.inspark.sabeel.auth.domain.port.input.AuthUseCases;
import com.inspark.sabeel.auth.domain.port.output.Auth;
import com.inspark.sabeel.auth.domain.port.output.Codes;
import com.inspark.sabeel.auth.domain.port.output.Emails;
import com.inspark.sabeel.auth.domain.utils.Generate6digitsCode;
import com.inspark.sabeel.auth.infrastructure.exception.ConflictException;
import com.inspark.sabeel.auth.infrastructure.exception.NotFoundException;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.output.Users;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCases {

    private final Auth authorisations;
    private final Emails emails;
    private final Codes codes;
    private final Users users;

    @Override
    public User signUp(User authUser) throws MessagingException {
//        Sign up the user
        var user = authorisations.signUp(authUser);
        var code = Generate6digitsCode.generate();
        Code codeToSave = Code.builder()
                .code(code)
                .status(CodeStatus.PENDING)
                .userId(user.getId())
                .build();
        codes.save(codeToSave);
//        Send the code to the user
        emails.sendEmail(
                user.getEmail(),
                EmailType.ACCOUNT_CONFIRMATION,
                user.getFullName(),
                code
        );
        return user;
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        return authorisations.authenticate(email, password);
    }

    @Override
    public void resetPassword(String receivedCode, String password, String confirmPassword) {
        Code code = checkCode(receivedCode);
        User user = users.findById(code.getUserId()).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND, code.getUserId())
        );
        if (!password.equals(confirmPassword)) {
            throw new ConflictException(ConflictException.ConflictExceptionType.PASSWORD_MISMATCH);
        }
        authorisations.resetPassword(user, password);
    }

    @Override
    public void changePassword(UUID userId,
                               String oldPassword,
                               String newPassword,
                               String confirmPassword) {
        var user = users.findById(userId).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND, userId)
        );
        if (!newPassword.equals(confirmPassword)) {
            throw new ConflictException(ConflictException.ConflictExceptionType.PASSWORD_MISMATCH);
        }
        try {
            authorisations.authenticate(user.getEmail(), oldPassword);
        } catch (Exception e) {
            throw new ConflictException(ConflictException.ConflictExceptionType.WRONG_PASSWORD);
        }
        authorisations.changePassword(user, newPassword);
    }

    @Override
    public void activateAccount(String receivedCode) throws MessagingException {
        Code code = checkCode(receivedCode);
        User user = users.findById(code.getUserId()).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND, code.getUserId())
        );
        if (!user.isEmailVerified() && code.getExpireAt().isBefore(LocalDateTime.now())) {
            this.authorisations.sendActivationCode(user);
        }
        authorisations.activateAccount(code, user);
    }

    @Override
    public void sendActivationCode(String email) throws MessagingException {
        //        Find the user by email
        var user = users.findByEmail(email).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND, email)
        );
        if (user.isEmailVerified()) {
            throw new ConflictException(ConflictException.ConflictExceptionType.EMAIL_ALREADY_VERIFIED);
        }
        this.authorisations.sendActivationCode(user);
    }

    @Override
    public void sendResetPasswordCode(String email) throws MessagingException {
        //        Find the user by email
        var user = users.findByEmail(email).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND, email)
        );

        var code = Generate6digitsCode.generate();
        Code codeToSave = Code.builder()
                .code(code)
                .status(CodeStatus.PENDING)
                .userId(user.getId())
                .build();
        codes.save(codeToSave);
        emails.sendEmail(
                user.getEmail(),
                EmailType.RESET_PASSWORD,
                user.getFullName(),
                code
        );
    }

    private Code checkCode(String receivedCode) {
        var code = codes.findByCode(receivedCode).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.CODE_NOT_FOUND, receivedCode)
        );
        if (code.getStatus().equals(CodeStatus.USED)) {
            throw new NotFoundException(NotFoundException.NotFoundExceptionType.CODE_USED, receivedCode);
        }
        if (code.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new NotFoundException(NotFoundException.NotFoundExceptionType.CODE_EXPIRED, receivedCode);
        }
        return code;
    }

}
