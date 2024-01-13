package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.persistence.crud.IUserCrudRepository;
import com.luheresbar.daily.persistence.entity.UserEntity;
import com.luheresbar.daily.persistence.entity.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final IUserCrudRepository userCrudRepository;

    public UserSecurityService(IUserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userCrudRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));

//        System.out.println(userEntity);

        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

        return User.builder()
                .username(userEntity.getEmail()) //Todo (se convirtio user id a string)
                .password(userEntity.getPassword())
                .authorities(this.grantedAuthorities(roles))
//                .accountLocked(userEntity.getLocked())
//                .disabled(userEntity.getDisabled())
                .build();
    }

    // para crear permisos especificos vamos a crear un metodo que reciba los roles que  previamente tiene el usuario y de
    // esta manera pueda adignar a demas de los roles, permisos individuales.
    private String[] getAuthorities(String role) {
        if ("ADMIN".equals(role) || "USER".equals(role)) {
            return new String[] {"specific_permission"}; // nombre de permiso puntual
        }

        return new String[] {};
    }

    private List<GrantedAuthority> grantedAuthorities(String[] roles) {

        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority: this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;

    }

}
