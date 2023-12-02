package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.domain.repository.IUserRepository;
import com.luheresbar.daily.persistence.crud.IUserCrudRepository;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.mapper.IUserMapper;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserEntityRepository implements IUserRepository {

    private final IUserCrudRepository userCrudRepository;
    private final IUserMapper userMapper;


    @Autowired
    public UserEntityRepository(IUserCrudRepository userCrudRepository, IUserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }


    public List<IUserSummary> viewUsersSummary() {
        return this.userCrudRepository.viewUsersSummary();
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        userMapper.toUser(userCrudRepository.save(userEntity));
        return user;
    }

    @Override
    public boolean existsById(String idUser) {
        return this.userCrudRepository.existsById(idUser);
    }


    @Override
    public Optional<User> getById(String userId) {
        Optional<UserEntity> userEntity = this.userCrudRepository.findById(userId);
        return userEntity.map(user -> this.userMapper.toUser(user));
    }

    @Override
    public boolean delete(String userId) {
        return this.userCrudRepository.findById(userId).map(user -> {
            this.userCrudRepository.delete(user);
            return true;
        }).orElse(false);
    }

    @Override
    public Integer countUsers() {
        return Math.toIntExact(this.userCrudRepository.count());
    }

    @Override
    @Transactional
    public void updateUserId(UpdateUserIdDto updateUserIdDto) {
        this.userCrudRepository.updateUserId(updateUserIdDto);
    }

}
