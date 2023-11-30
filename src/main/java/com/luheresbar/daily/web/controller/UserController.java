package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private String currentUser;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        this.currentUser = (String) authentication.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    // Como usuario puedo visualizar mi informacion personal registrada en la app, para que pueda saber si debo actualizarla
    @GetMapping("/user")
    public ResponseEntity<Optional<User>> viewInformation() {
        return ResponseEntity.ok(userService.getById(currentUser));
    }

    // Como usuario puedo actualizar mi informacion personal registrada en la app para que pueda tener la informacion actualizada.
    @PutMapping("/user")
    public ResponseEntity<User> update(@RequestBody User user) {
        user.setUserId(currentUser);
        Optional<User> userInDb = this.userService.getById(currentUser);

        if (user.getUsername() == null) {
            user.setUserId(userInDb.get().getUserId());
        }
        if (user.getPassword() == null) {
            user.setPassword(userInDb.get().getPassword());
        }
        if (user.getEmail() == null) {
            user.setEmail(userInDb.get().getEmail());
        }
        return ResponseEntity.ok(this.userService.save(user));
    }

}
