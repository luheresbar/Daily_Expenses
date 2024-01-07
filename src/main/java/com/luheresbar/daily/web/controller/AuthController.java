package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.Category;
import com.luheresbar.daily.domain.User;
import com.luheresbar.daily.domain.UserRole;
import com.luheresbar.daily.domain.dto.LoginDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.CategoryService;
import com.luheresbar.daily.domain.service.UserRoleService;
import com.luheresbar.daily.domain.service.UserService;
import com.luheresbar.daily.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRoleService userRoleService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, UserRoleService userRoleService, CategoryService categoryService, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRoleService = userRoleService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
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
            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            this.userService.save(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRole("USER");
            userRole.setGrantedDate(currentDate);
            this.userRoleService.save(userRole);

            Category category = new Category();
            category.setUserId(user.getUserId());
            category.setCategoryName("Others");
            this.categoryService.save(category);

            Account account = new Account();
            account.setUserId(user.getUserId());
            account.setAccountName("Cash");
            account.setAvailableMoney(0.0);
            account.setAvailable(true);
            this.accountService.save(account);

            String jwt = this.jwtUtil.create(user.getUserId());
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
        }
        return ResponseEntity.badRequest().build();

    }

}
