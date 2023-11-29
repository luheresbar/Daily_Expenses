package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.UserRol;

public interface IUserRolRepository {

    void save(UserRol userRol);

    boolean existById(UserRol userRol);

}
