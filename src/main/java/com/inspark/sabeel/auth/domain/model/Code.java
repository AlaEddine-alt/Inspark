package com.inspark.sabeel.auth.domain.model;

import com.inspark.sabeel.auth.domain.enums.CodeStatus;
import com.inspark.sabeel.common.BaseModel;
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
public class Code extends BaseModel {

    private String code;
    private LocalDateTime expireAt;
    private CodeStatus status;
    private UUID userId;
}
