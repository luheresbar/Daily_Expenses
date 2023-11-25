package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.User;

import java.util.List;

public interface IUserRepository {

    List<User> getAll();

    User save(User user);

}
