package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;


    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    };

}
