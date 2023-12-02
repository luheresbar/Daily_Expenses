package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserCrudRepository extends CrudRepository<UserEntity, String> {

    // anotación @Query con JPQL
    @Query("SELECT u.userId as userId, u.registerDate as registerDate FROM UserEntity u")
    List<IUserSummary> viewUsersSummary();

    @Query(value =  "UPDATE users " +
            "SET user_id = :#{#updateUserId.newUserId} " +
            "WHERE user_id = :#{#updateUserId.currentUserId}", nativeQuery = true)
    @Modifying
    void updateUserId(@Param("updateUserId") UpdateUserIdDto updateUserIdDto);

}
