package com.inspark.sabeel.auth.infrastructure.repository;

import com.inspark.sabeel.auth.infrastructure.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, UUID> {

    Optional<CodeEntity> findByCode(String code);

    Optional<CodeEntity> findByUserId(UUID userId);

}
