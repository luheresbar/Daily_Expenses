package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T22:26:28-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public User toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userEntity.getUserId() );
        user.setUsername( userEntity.getUsername() );
        user.setPassword( userEntity.getPassword() );
        user.setEmail( userEntity.getEmail() );
        if ( userEntity.getRegisterDate() != null ) {
            user.setRegisterDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( userEntity.getRegisterDate() ) );
        }
        user.setRoles( userRoleEntityListToUserRoleList( userEntity.getRoles() ) );

        return user;
    }

    @Override
    public List<User> toUsers(List<UserEntity> userEntities) {
        if ( userEntities == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userEntities.size() );
        for ( UserEntity userEntity : userEntities ) {
            list.add( toUser( userEntity ) );
        }

        return list;
    }

    @Override
    public UserEntity toUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserId( user.getUserId() );
        userEntity.setUsername( user.getUsername() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setEmail( user.getEmail() );
        if ( user.getRegisterDate() != null ) {
            userEntity.setRegisterDate( LocalDateTime.parse( user.getRegisterDate() ) );
        }

        return userEntity;
    }

    protected UserRole userRoleEntityToUserRole(UserRoleEntity userRoleEntity) {
        if ( userRoleEntity == null ) {
            return null;
        }

        UserRole userRole = new UserRole();

        userRole.setRole( userRoleEntity.getRole() );
        userRole.setUserId( userRoleEntity.getUserId() );
        if ( userRoleEntity.getGrantedDate() != null ) {
            userRole.setGrantedDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( userRoleEntity.getGrantedDate() ) );
        }

        return userRole;
    }

    protected List<UserRole> userRoleEntityListToUserRoleList(List<UserRoleEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<UserRole> list1 = new ArrayList<UserRole>( list.size() );
        for ( UserRoleEntity userRoleEntity : list ) {
            list1.add( userRoleEntityToUserRole( userRoleEntity ) );
        }

        return list1;
    }
}
