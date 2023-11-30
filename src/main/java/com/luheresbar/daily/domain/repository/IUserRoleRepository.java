package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.UserRole;

import java.util.List;

public interface IUserRoleRepository {

    void save(UserRole userRole);

    boolean existsById(UserRole userRole);

    void delete(UserRole userRole);

    List<UserRole> getAll();
}
