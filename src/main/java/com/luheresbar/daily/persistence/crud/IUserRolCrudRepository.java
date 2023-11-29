package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import com.luheresbar.daily.persistence.entity.UserRolePK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface IUserRolCrudRepository extends CrudRepository<UserRoleEntity, UserRolePK> {
}
