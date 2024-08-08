package com.inspark.sabeel.user.infrastructure.mapper;


import com.inspark.sabeel.auth.domain.model.Role;
import com.inspark.sabeel.auth.infrastructure.dto.request.SignUpDto;
import com.inspark.sabeel.auth.infrastructure.dto.response.SignUpResponseDto;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.dto.UserDto;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User toAuthUserFromDto(SignUpDto signupDto);

    public abstract UserEntity toUserEntity(User authUser);

    public abstract User toUser(UserEntity userEntity);

    public abstract UserDto toUserDto(User authUser);

    @Mapping(target = "roles", expression = "java(mapRolesToRoleNames(authUser.getRoles()))")
    public abstract SignUpResponseDto toSignUpResponseDto(User authUser);

    public Set<String> mapRolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
