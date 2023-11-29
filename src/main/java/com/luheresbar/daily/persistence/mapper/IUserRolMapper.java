package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.UserRol;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserRolMapper {

    UserRol toUserRol(UserRoleEntity userRoleEntity);

    List<UserRol> toUserRols(List<UserRoleEntity> userRoleEntities);

    @Mapping(target = "user", ignore = true)
    UserRoleEntity toUserRoleEntity(UserRol userRol);

}
