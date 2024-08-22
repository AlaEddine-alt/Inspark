package com.inspark.sabeel.auth.domain.port.output;

import com.inspark.sabeel.auth.domain.model.AccessToken;
import com.inspark.sabeel.auth.domain.model.Code;
import com.inspark.sabeel.user.domain.model.User;
import jakarta.mail.MessagingException;

public interface Auth {

    User signUp(User authUser);

    AccessToken signIn(String email, String password);

    void resetPassword(User user, String password);

    void changePassword(User user, String newPassword);

    void activateAccount(Code code, User user) throws MessagingException;

    void sendActivationCode(User user) throws MessagingException;
}
