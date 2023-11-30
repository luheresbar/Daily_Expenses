package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.repository.IUserRoleRepository;
import com.luheresbar.daily.persistence.crud.IUserRoleCrudRepository;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import com.luheresbar.daily.persistence.entity.UserRolePK;
import com.luheresbar.daily.persistence.mapper.IUserRolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleEntityRepository implements IUserRoleRepository {

    private final IUserRoleCrudRepository userRolCrudRepository;
    private final IUserRolMapper userRolMapper;

    @Autowired
    public UserRoleEntityRepository(IUserRoleCrudRepository userRolCrudRepository, IUserRolMapper userRolMapper) {
        this.userRolCrudRepository = userRolCrudRepository;
        this.userRolMapper = userRolMapper;
    }

    @Override
    public List<UserRole> getAll() {
        List<UserRoleEntity> userRoleEntity = (List<UserRoleEntity>) this.userRolCrudRepository.findAll();
        return this.userRolMapper.toUserRols(userRoleEntity);
    }

    @Override
    public void save(UserRole userRole) {
        UserRoleEntity userRoleEntity = this.userRolMapper.toUserRoleEntity(userRole);
        this.userRolCrudRepository.save(userRoleEntity);
    }

    @Override
    public boolean existsById(UserRole userRole) {
        UserRoleEntity userRoleEntity = this.userRolMapper.toUserRoleEntity(userRole);
        UserRolePK userRolePK = new UserRolePK(userRoleEntity.getRole(), userRoleEntity.getUserId());
        return this.userRolCrudRepository.existsById(userRolePK);
    }

    @Override
    public void delete(UserRole userRole) {
        UserRoleEntity userRoleEntity = this.userRolMapper.toUserRoleEntity(userRole);
        UserRolePK userRolePK = new UserRolePK(userRoleEntity.getRole(), userRoleEntity.getUserId());
        this.userRolCrudRepository.deleteById(userRolePK);
    }


}
