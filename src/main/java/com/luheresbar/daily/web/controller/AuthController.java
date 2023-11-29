package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.UserRol;
import com.luheresbar.daily.domain.dto.LoginDto;
import com.luheresbar.daily.domain.service.UserRolService;
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
    private final UserRolService userRolService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, UserRolService userRolService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRolService = userRolService;
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
        if (user.getUserId() == null || !this.userService.exist(user.getUserId())) {
            this.userService.save(user);

            UserRol userRol = new UserRol();
            userRol.setUserId(user.getUserId());
            userRol.setRole("USER");
            userRol.setGrantedDate(String.valueOf(LocalDateTime.now()));
            this.userRolService.save(userRol);

            String jwt = this.jwtUtil.create(user.getUserId());
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
        }
        return ResponseEntity.badRequest().build();

    }

}
