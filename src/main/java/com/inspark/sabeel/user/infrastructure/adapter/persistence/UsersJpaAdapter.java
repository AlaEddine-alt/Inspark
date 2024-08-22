package com.inspark.sabeel.user.infrastructure.adapter.persistence;

import com.inspark.sabeel.auth.domain.model.Role;
import com.inspark.sabeel.exception.BadRequestException;
import com.inspark.sabeel.exception.ConflictException;
import com.inspark.sabeel.user.infrastructure.adapter.specifications.UserSpec;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.output.Users;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import com.inspark.sabeel.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersJpaAdapter implements Users {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toUser);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUser);
    }

    @Override
    public User update(User user) {
        try{
        return userMapper.toUser(userRepository.save(userMapper.toUserEntity(user)));
    } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException ex) {
        throw new ConflictException(ConflictException.ConflictExceptionType.CONFLICT_LOCK_VERSION);
    }
    }


    @Override
    public Page<User> findAll(String criteria, Pageable pageable) {
        return userRepository.findAll(UserSpec.hasCriteria(criteria), pageable)
                .map(userMapper::toUser);
    }

    @Override
    public void deleteUser(User userToDelete, String currentUserRole) {
        // Get roles from the user's role set
        Set<String> rolesOfUserToDelete = userToDelete.getRoles().stream()
                .map(Role::getName) // Assuming Role has a getName() method that returns a String
                .collect(Collectors.toSet());
        // Role-based deletion logic
        switch (currentUserRole) {
            case "SUPERADMIN":
                if (rolesOfUserToDelete.contains("SUPERADMIN")) {
                    throw new BadRequestException(BadRequestException.BadRequestExceptionType.PERMISSION_DENIED);
                }
                break;

            case "ADMIN":
                if (rolesOfUserToDelete.contains("ADMIN") || rolesOfUserToDelete.contains("SUPERADMIN")) {
                    throw new BadRequestException(BadRequestException.BadRequestExceptionType.PERMISSION_DENIED);
                }
                break;

            default:
                throw new BadRequestException(BadRequestException.BadRequestExceptionType.PERMISSION_DENIED);
        }

        userRepository.delete(userMapper.toUserEntity(userToDelete));

    }


}
