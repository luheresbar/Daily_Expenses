package com.luheresbar.daily.persistence;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.domain.repository.IUserRepository;
import com.luheresbar.daily.persistence.crud.IUserCrudRepository;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.mapper.IUserMapper;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserEntityRepository implements IUserRepository {

    private final IUserCrudRepository userCrudRepository;
    private final IUserMapper userMapper;


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
    public boolean existsByEmail(String email) {
        return this.userCrudRepository.existsByEmail(email);
    }
    @Override
    public boolean existsById(Integer userId) {
        return this.userCrudRepository.existsById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        Optional<UserEntity> userEntity =  this.userCrudRepository.findUserByEmail(email);
        return userEntity.map(user -> this.userMapper.toUser(user));
    }

    @Override
    public Optional<User> getById(Integer userId) {
        Optional<UserEntity> userEntity = this.userCrudRepository.findById(userId);
        return userEntity.map(user -> this.userMapper.toUser(user));
    }

    @Override
    public boolean delete(Integer userId) {
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

    @Override
    public boolean changePassword(String email, String newPassword) {
        if (this.userCrudRepository.changePassword(email, newPassword) > 0) {
            return true;
        } else {
            return false;
        }
    }

}
