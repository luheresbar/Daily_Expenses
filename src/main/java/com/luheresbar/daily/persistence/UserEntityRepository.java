package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.repository.IUserRepository;
import com.luheresbar.daily.persistence.crud.IUserCrudRepository;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEntityRepository implements IUserRepository {

    private final IUserCrudRepository userCrudRepository;
    private final IUserMapper userMapper;

    @Autowired
    public UserEntityRepository(IUserCrudRepository userCrudRepository, IUserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAll() {
        List<UserEntity> users = (List<UserEntity>) userCrudRepository.findAll();
        return userMapper.toUsers(users);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        return userMapper.toUser(userCrudRepository.save(userEntity));
    }

}
