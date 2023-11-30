package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserRolMapper {

    UserRole toUserRol(UserRoleEntity userRoleEntity);

    List<UserRole> toUserRols(List<UserRoleEntity> userRoleEntities);

    @Mapping(target = "user", ignore = true)
    UserRoleEntity toUserRoleEntity(UserRole userRole);

}
