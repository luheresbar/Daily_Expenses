package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.repository.IUserRoleRepository;
import com.luheresbar.daily.persistence.entity.UserRolePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final IUserRoleRepository userRolRepository;

    @Autowired
    public UserRoleService(IUserRoleRepository userRolRepository) {
        this.userRolRepository = userRolRepository;
    }

    public void save(UserRole userRole) {
        this.userRolRepository.save(userRole);
    }

    public boolean exists(UserRole userRole) {
        return userRolRepository.existsById(userRole);
    }

    public boolean delete(UserRolePK userRolePK) {
        return this.userRolRepository.delete(userRolePK);

    }

    public List<UserRole> getAll() {
        return this.userRolRepository.getAll();
    }
}
