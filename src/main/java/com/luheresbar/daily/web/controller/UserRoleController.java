package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    // Como usuario con Rol ADMIN puedo otorgar roles a otros usuarios para que dichos usuarios tengan acceso a funcionalidades adicionales en la app.
    @PostMapping("/user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> grantRole(@RequestBody UserRole userRole) {
        if (userRole == null || userRoleService.exists(userRole) || userRole.getRole().equals("USER")) {
            return ResponseEntity.badRequest().build();
        }
        userRole.setGrantedDate(String.valueOf(LocalDateTime.now()));
        this.userRoleService.save(userRole);
        return ResponseEntity.ok().build();
    }

    // Como usuario con rol ADMIN puedo quitar a otros usuarios sus roles que permiten acceder a funcionalidades adicionales en la app.
    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> removeRole(UserRole userRole) {
        if(!this.userRoleService.exists(userRole) || userRole.getRole().equals("USER")) {
            return ResponseEntity.badRequest().build();
        }
        this.userRoleService.delete(userRole);
        return ResponseEntity.ok().build();

    }

}
