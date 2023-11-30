package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.UserRole;

public interface IUserRoleRepository {

    void save(UserRole userRole);

    boolean existsById(UserRole userRole);

    void delete(UserRole userRole);

}
