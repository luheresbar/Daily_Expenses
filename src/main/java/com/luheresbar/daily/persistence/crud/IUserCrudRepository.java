package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserCrudRepository extends CrudRepository<UserEntity, String> {

    // anotación @Query con JPQL
    @Query("SELECT u.userId as userId, u.registerDate as registerDate FROM UserEntity u")
    List<IUserSummary> viewUsersSummary();

}
