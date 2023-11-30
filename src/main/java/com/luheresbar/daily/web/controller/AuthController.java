package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.dto.LoginDto;
import com.luheresbar.daily.domain.service.UserRoleService;
import com.luheresbar.daily.domain.service.UserService;
import com.luheresbar.daily.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRoleService userRoleService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, UserRoleService userRoleService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        String jwt = this.jwtUtil.create(loginDto.getUserId());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody User user) {
        if (user.getUserId() == null || !this.userService.exists(user.getUserId())) {

            String currentDate = String.valueOf(LocalDateTime.now());
            user.setRegisterDate(currentDate);
            this.userService.save(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRole("USER");
            userRole.setGrantedDate(currentDate);
            this.userRoleService.save(userRole);

            String jwt = this.jwtUtil.create(user.getUserId());
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
        }
        return ResponseEntity.badRequest().build();

    }

}
