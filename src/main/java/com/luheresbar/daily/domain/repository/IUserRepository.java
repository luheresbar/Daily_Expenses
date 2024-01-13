package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.persistence.projections.IUserSummary;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<IUserSummary> viewUsersSummary();

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    boolean existsById(Integer userId);

    Optional<User> getById(Integer userId);

    boolean delete(Integer userId);

    Integer countUsers();

    void updateUserId(UpdateUserIdDto updateUserIdDto);
}
