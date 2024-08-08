package com.inspark.sabeel.user.infrastructure.adapter.persistence;

import com.inspark.sabeel.auth.infrastructure.adapter.specifications.UserSpec;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.output.Users;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import com.inspark.sabeel.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
        return userMapper.toUser(userRepository.save(userMapper.toUserEntity(user)));
    }


    @Override
    public Page<User> findAll(String criteria, Pageable pageable) {
        return userRepository.findAll(UserSpec.hasCriteria(criteria), pageable)
                .map(userMapper::toUser);
    }
}
