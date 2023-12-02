package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.persistence.projections.IUserSummary;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<IUserSummary> viewUsersSummary();

    User save(User user);

    boolean existsById(String idUser);

    Optional<User> getById(String userId);

    boolean delete(String userId);

    Integer countUsers();

    void updateUserId(UpdateUserIdDto updateUserIdDto);
}
