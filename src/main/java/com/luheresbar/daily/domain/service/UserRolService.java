package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.UserRol;
import com.luheresbar.daily.domain.repository.IUserRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRolService {

    private final IUserRolRepository userRolRepository;

    @Autowired
    public UserRolService(IUserRolRepository userRolRepository) {
        this.userRolRepository = userRolRepository;
    }

    public void save(UserRol userRol) {
        this.userRolRepository.save(userRol);
    }

    public boolean exist(UserRol userRol) {
        return userRolRepository.existById(userRol);
    }

}
