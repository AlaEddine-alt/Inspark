package com.inspark.sabeel.user.domain.port.input;

import com.inspark.sabeel.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsersUseCases {

    User findById(UUID id);


    User update(User user);

    void toggleEnableDisable(UUID id);

    Page<User> findAllWithPaginationAndFiltering(String criteria, Pageable pageable);

    void deleteById(UUID userId,String currentUserRole);


}
