package com.inspark.sabeel.user.domain.port.output;

import com.inspark.sabeel.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface Users {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    User update(User user);


    Page<User> findAll(String criteria, Pageable pageable);

    void deleteUser(User userToDelete,String currentUserRole);

}
