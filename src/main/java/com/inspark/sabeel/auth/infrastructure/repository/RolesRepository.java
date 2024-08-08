package com.inspark.sabeel.auth.infrastructure.repository;

import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolesRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(String name);
}
