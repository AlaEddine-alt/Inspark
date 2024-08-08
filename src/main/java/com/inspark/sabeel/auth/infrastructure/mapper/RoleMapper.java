package com.inspark.sabeel.auth.infrastructure.mapper;

import com.inspark.sabeel.auth.domain.model.Role;
import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
    public abstract RoleEntity toRoleEntity(Role role);

    public abstract Role toRole(RoleEntity roleEntity);

}
