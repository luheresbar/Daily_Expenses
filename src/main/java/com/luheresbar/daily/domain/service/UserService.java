package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;


    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Secured("ROLE_ADMIN")
    public List<User> getAll() {
        return userRepository.getAll();
    }



    public void save(User user) {
        userRepository.save(user);
    };


    public boolean exist(String idUser) {
        return this.userRepository.existById(idUser);
    }

    public Optional<User> getById(String userId) {
        return userRepository.getById(userId);
    }
}
