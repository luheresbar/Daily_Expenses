package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-15T16:39:04-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class IUserRolMapperImpl implements IUserRolMapper {

    @Override
    public UserRole toUserRol(UserRoleEntity userRoleEntity) {
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

    @Override
    public List<UserRole> toUserRols(List<UserRoleEntity> userRoleEntities) {
        if ( userRoleEntities == null ) {
            return null;
        }

        List<UserRole> list = new ArrayList<UserRole>( userRoleEntities.size() );
        for ( UserRoleEntity userRoleEntity : userRoleEntities ) {
            list.add( toUserRol( userRoleEntity ) );
        }

        return list;
    }

    @Override
    public UserRoleEntity toUserRoleEntity(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();

        userRoleEntity.setRole( userRole.getRole() );
        userRoleEntity.setUserId( userRole.getUserId() );
        if ( userRole.getGrantedDate() != null ) {
            userRoleEntity.setGrantedDate( LocalDateTime.parse( userRole.getGrantedDate() ) );
        }

        return userRoleEntity;
    }
}
