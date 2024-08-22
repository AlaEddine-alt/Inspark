package com.inspark.sabeel.user.domain.service;

import com.inspark.sabeel.exception.NotFoundException;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.input.UsersUseCases;
import com.inspark.sabeel.user.domain.port.output.Users;
import com.inspark.sabeel.user.application.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UsersUseCases {

    private final Users users;

    @Override
    public User findById(UUID id) {
        return users.findById(id).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND)
        );
    }

    @Override
    public User updateProfile(UUID userId, UserDto profileDto) {
        var user = users.findById(userId).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND)
        );

        // Update user fields
        user.setFirstName(profileDto.firstName());
        user.setLastName(profileDto.lastName());
        user.setPhoneNumber(profileDto.phoneNumber());
        user.setEmail(profileDto.email());
        user.setSkills(profileDto.skills());

        return users.update(user);
    }


    @Override
    public void toggleEnableDisable(UUID id) {
        var user = users.findById(id).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND)
        );
        user.setEnabled(!user.isEnabled());
        users.update(user);
    }

    @Override
    public Page<User> findAllWithPaginationAndFiltering(String criteria, Pageable pageable) {
        return users.findAll(criteria, pageable);
    }

    //delete
    @Override
    public void deleteById(UUID id,String currentUserRole) {
        var userToDelete = users.findById(id).orElseThrow(
                () -> new NotFoundException(NotFoundException.NotFoundExceptionType.USER_NOT_FOUND)
        );
        users.deleteUser(userToDelete,currentUserRole);
    }
    }

