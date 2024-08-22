package com.inspark.sabeel.auth.infrastructure.utils;

import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import com.inspark.sabeel.auth.infrastructure.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesInitializer {

    private final RolesRepository rolesRepository;

    public void createRoles() {
        if (rolesRepository.findByName("USER") == null) {
            rolesRepository.save(RoleEntity.builder()
                    .name("USER")
                    .build());
        }
        if (rolesRepository.findByName("ADMIN") == null) {
            rolesRepository.save(RoleEntity.builder()
                    .name("ADMIN")
                    .build());
        }
        if (rolesRepository.findByName("SUPERADMIN") == null) {
            rolesRepository.save(RoleEntity.builder()
                    .name("SUPERADMIN")
                    .build());
        }
    }
}