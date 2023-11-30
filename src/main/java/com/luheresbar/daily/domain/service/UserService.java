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
    // Nota: modificar esta funcionalidad a solamente poder acceder a la cantidada de usuarios y su fecha de creacion del usuario
    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean exists(String idUser) {
        return this.userRepository.existsById(idUser);
    }

    public Optional<User> getById(String userId) {
        return userRepository.getById(userId);
    }
}
