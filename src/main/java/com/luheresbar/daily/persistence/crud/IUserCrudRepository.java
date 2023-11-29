package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface IUserCrudRepository extends CrudRepository<UserEntity, String> {



}
