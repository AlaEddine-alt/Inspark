package com.inspark.sabeel.auth.domain.port.input;


import com.inspark.sabeel.auth.domain.model.AccessToken;
import com.inspark.sabeel.user.domain.model.User;
import jakarta.mail.MessagingException;

import java.util.UUID;

/**
 * Interface representing the use cases for authentication.
 */
public interface AuthUseCases {

    /**
     * Signs up a new user.
     *
     * @param authUser the user to be signed up
     * @return the signed-up user
     */
    User signUp(User authUser) throws MessagingException;

    AccessToken authenticate(String email, String password);

    void resetPassword(String code, String password, String confirmPassword);

    void changePassword(UUID userId, String oldPassword, String newPassword, String confirmPassword);

    void activateAccount(String code) throws MessagingException;

    void sendActivationCode(String email) throws MessagingException;

    void sendResetPasswordCode(String email) throws MessagingException;
}