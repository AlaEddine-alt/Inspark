package com.inspark.sabeel.auth.infrastructure.entity;

import com.inspark.sabeel.auth.domain.enums.CodeStatus;
import com.inspark.sabeel.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "codes")
public class CodeEntity extends BaseEntity {
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expiredAt", nullable = false)
    private LocalDateTime expireAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CodeStatus status;

    @Column(name = "userId", nullable = false)
    private UUID userId;


}
