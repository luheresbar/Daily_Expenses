package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.dto.UpdateUserIdDto;
import com.luheresbar.daily.domain.dto.UserProfileDto;
import com.luheresbar.daily.domain.service.UserService;
import com.luheresbar.daily.persistence.projections.IUserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private Integer currentUser;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    // Listar los usuarios registrados solamente con su userId y su fecha de registro
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<IUserSummary>> viewUsersSummary() {
        return new ResponseEntity<>(userService.viewUsersSummary(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countUsers() {
        return ResponseEntity.ok(this.userService.countUsers());
    }

    // Como usuario puedo visualizar mi informacion personal registrada en la app, para que pueda saber si debo actualizarla
    @GetMapping("/user")
    public ResponseEntity<Optional<UserProfileDto>> viewInformation() {
        Optional<User> userDB = userService.getById(this.currentUser);
        return ResponseEntity.ok(Optional.of(new UserProfileDto(
                userDB.get().getUserId(),
                userDB.get().getUsername(),
                userDB.get().getEmail(),
                userDB.get().getRegisterDate()
                ))
        );
    }

    // Como usuario puedo actualizar mi informacion personal registrada en la app para que pueda tener la informacion actualizada.
    @PutMapping("/user")
    public ResponseEntity<User> update(@RequestBody User user) {
        user.setUserId(this.currentUser);
        Optional<User> userInDb = this.userService.getById(this.currentUser);

        if (user.getUsername() == null) {
            user.setUsername(userInDb.get().getUsername());
        }
        if (user.getPassword() == null) {
            user.setPassword(userInDb.get().getPassword());
        } else if (!user.getPassword().equals(userInDb.get().getPassword())) {
            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        if (user.getEmail() == null) {
            user.setEmail(userInDb.get().getEmail());
        }
        return ResponseEntity.ok(this.userService.save(user));
    }

     // Actualizar el userId de un usuario.
    @PatchMapping("/update/userid")
    public ResponseEntity<Optional<User>> updateUserId(@RequestBody UpdateUserIdDto updateUserIdDto) {
        updateUserIdDto.setCurrentUserId(this.currentUser);
        if (this.userService.exists(currentUser)) {
            this.userService.updateUserId(updateUserIdDto);
            return ResponseEntity.ok(this.userService.getById(updateUserIdDto.getNewUserId()));
        }
        return ResponseEntity.notFound().build();
    }


    //  Unicamente un usuario puede eliminar su propia cuenta.
    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser() {
        if (this.userService.delete(this.currentUser)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
