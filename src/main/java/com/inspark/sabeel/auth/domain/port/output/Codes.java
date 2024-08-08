package com.inspark.sabeel.auth.domain.port.output;


import com.inspark.sabeel.auth.domain.model.Code;

import java.util.Optional;
import java.util.UUID;

public interface Codes {

    void save(Code code);

    Optional<Code> findByCode(String code);

    void update(Code code);

    Optional<Code> findByUserId(UUID userId);

    boolean deleteByCode(UUID code);
}
