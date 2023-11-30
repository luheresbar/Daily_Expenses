package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<User> getAll();

    User save(User user);

    boolean existsById(String idUser);

    Optional<User> getById(String userId);
}
