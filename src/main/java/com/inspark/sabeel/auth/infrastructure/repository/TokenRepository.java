package com.inspark.sabeel.auth.infrastructure.repository;

import com.inspark.sabeel.auth.infrastructure.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    Optional<TokenEntity> findByToken(String token);

    @Query("SELECT t FROM TokenEntity t WHERE t.userId = :userId AND (t.expired = false OR t.revoked = false)")
    List<TokenEntity> findAllNonExpiredOrNonRevokedByUserId(@Param("userId") UUID userId);
}
