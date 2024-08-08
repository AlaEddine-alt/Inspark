package com.inspark.sabeel.auth.infrastructure.adapter.persistence;

import com.inspark.sabeel.auth.domain.model.Code;
import com.inspark.sabeel.auth.domain.port.output.Codes;
import com.inspark.sabeel.auth.infrastructure.exception.NotFoundException;
import com.inspark.sabeel.auth.infrastructure.mapper.CodeMapper;
import com.inspark.sabeel.auth.infrastructure.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CodeJpaAdapter implements Codes {

    @Value("${application.auth-code.expiration}")
    private Long expirationTime;

    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;

    @Override
    public void save(Code code) {
        var expireAt = LocalDateTime.now().plusSeconds(expirationTime);
        code.setExpireAt(expireAt);
        var codeEntity = codeMapper.toCodeEntity(code);
        codeRepository.save(codeEntity);
    }

    @Override
    public Optional<Code> findByCode(String code) {
        return codeRepository.findByCode(code)
                .map(codeMapper::toCode);
    }

    @Override
    public void update(Code code) {
        var codeEntity = codeMapper.toCodeEntity(code);
        codeRepository.save(codeEntity);
    }

    @Override
    public Optional<Code> findByUserId(UUID userId) {
        return codeRepository.findByUserId(userId)
                .map(codeMapper::toCode);
    }

    @Override
    public boolean deleteByCode(UUID code) {
        var codeEntity = codeRepository.findById(code)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundException.NotFoundExceptionType.GENERIC, "Code not found")
                );
        codeRepository.delete(codeEntity);
        return true;

    }
}
