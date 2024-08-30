package com.inspark.sabeel.common;

import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    private AuthUtils() {
    }

    public static User getCurrentAuthenticatedUser(UserMapper userMapper) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        return switch (principal) {
            case User user -> user;
            case UserEntity userEntity -> userMapper.toUser(userEntity);
            default -> throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
        };

    }

    public static String getCurrentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
