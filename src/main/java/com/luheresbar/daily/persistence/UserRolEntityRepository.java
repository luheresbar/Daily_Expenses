package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.UserRol;
import com.luheresbar.daily.domain.repository.IUserRolRepository;
import com.luheresbar.daily.persistence.crud.IUserRolCrudRepository;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import com.luheresbar.daily.persistence.entity.UserRolePK;
import com.luheresbar.daily.persistence.mapper.IUserRolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRolEntityRepository implements IUserRolRepository {

    private final IUserRolCrudRepository userRolCrudRepository;
    private final IUserRolMapper userRolMapper;

    @Autowired
    public UserRolEntityRepository(IUserRolCrudRepository userRolCrudRepository, IUserRolMapper userRolMapper) {
        this.userRolCrudRepository = userRolCrudRepository;
        this.userRolMapper = userRolMapper;
    }

    @Override
    public void save(UserRol userRol) {
        UserRoleEntity userRoleEntity = this.userRolMapper.toUserRoleEntity(userRol);
        this.userRolCrudRepository.save(userRoleEntity);
    }

    @Override
    public boolean existById(UserRol userRol) {
        UserRoleEntity userRoleEntity = this.userRolMapper.toUserRoleEntity(userRol);
        UserRolePK userRolePK = new UserRolePK(userRoleEntity.getRole(), userRoleEntity.getUserId());
        return this.userRolCrudRepository.existsById(userRolePK);
    }

}
