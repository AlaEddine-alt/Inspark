package com.inspark.sabeel.auth.infrastructure.mapper;

import com.inspark.sabeel.auth.domain.model.Code;
import com.inspark.sabeel.auth.infrastructure.entity.CodeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CodeMapper {

    public abstract CodeEntity toCodeEntity(Code code);

    public abstract Code toCode(CodeEntity codeEntity);
}
