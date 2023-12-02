package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.persistence.entity.UserRolePK;

import java.util.List;

public interface IUserRoleRepository {

    void save(UserRole userRole);

    boolean existsById(UserRole userRole);

    boolean delete(UserRolePK userRolePK);

    List<UserRole> getAll();
}
