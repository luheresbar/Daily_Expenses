package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserCrudRepository extends CrudRepository<UserEntity, Integer> {

    // anotación @Query con JPQL
    @Query("SELECT u.userId as userId, u.registerDate as registerDate FROM UserEntity u")
    List<IUserSummary> viewUsersSummary();

    // Query JPQL
    @Query("UPDATE UserEntity u " +
            "SET u.userId = :#{#updateUserId.newUserId} " +
            "WHERE u.userId = :#{#updateUserId.currentUserId}")
    @Modifying
    void updateUserId(@Param("updateUserId") UpdateUserIdDto updateUserIdDto);

    @Query(value = "SELECT e " +
            "FROM UserEntity e " +
            "WHERE email = :email ")
    Optional<UserEntity> findUserByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :newPassword WHERE u.email = :email")
    int changePassword(@Param("email") String email, @Param("newPassword") String newPassword);


    // Query Nativo
//    @Query(value =  "UPDATE users " +
//            "SET user_id = :#{#updateUserId.newUserId} " +
//            "WHERE user_id = :#{#updateUserId.currentUserId}", nativeQuery = true)
//    @Modifying
//    void updateUserId(@Param("updateUserId") UpdateUserIdDto updateUserIdDto);

}
