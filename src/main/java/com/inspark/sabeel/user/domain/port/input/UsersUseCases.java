package com.inspark.sabeel.user.domain.port.input;

import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsersUseCases {

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user associated with the given id
     */
    User findById(UUID id);

    /**
     * Updates the profile of a user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     * @param profileDto the new profile information
     * @return the updated user
     */
    User updateProfile(UUID userId, UserDto profileDto);

    /**
     * Toggles the enabled/disabled status of a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     */
    void toggleEnableDisable(UUID id);

    /**
     * Finds all users with pagination and filtering based on the given criteria.
     *
     * @param criteria the criteria for filtering users
     * @param pageable the pagination information
     * @return a page of users that match the given criteria
     */
    Page<User> findAllWithPaginationAndFiltering(String criteria, Pageable pageable);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     */
    void deleteById(UUID userId,String currentUserRole);


}
