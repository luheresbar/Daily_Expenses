package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User toUser(UserEntity userEntity);

    List<User> toUsers(List<UserEntity> userEntities);


    @Mappings({
            @Mapping(target = "categories", ignore = true),
            @Mapping(target = "accounts", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    UserEntity toUserEntity(User user);

}
