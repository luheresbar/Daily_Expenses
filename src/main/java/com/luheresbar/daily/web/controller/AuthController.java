package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.*;
import com.luheresbar.daily.domain.dto.*;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.CategoryService;
import com.luheresbar.daily.domain.service.UserRoleService;
import com.luheresbar.daily.domain.service.UserService;
import com.luheresbar.daily.web.config.JwtUtil;
import com.luheresbar.daily.web.mailManager.MailManager;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final MailManager mailManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService, JwtUtil jwtUtil,
            UserRoleService userRoleService,
            CategoryService categoryService,
            AccountService accountService,
            PasswordEncoder passwordEncoder,
            MailManager mailManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRoleService = userRoleService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.mailManager = mailManager;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        if (loginDto.getEmail() != null && this.userService.existsByEmail(loginDto.getEmail())) {
            Optional<User> userDb = this.userService.findUserByEmail(loginDto.getEmail());
            int userId = userDb.get().getUserId();
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userId, loginDto.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(login);

            String access_token = this.jwtUtil.createJwt(userId);
            String refresh_token = this.jwtUtil.createJwtRefresh(userId);
            return ResponseEntity.ok(new TokenDto(access_token, refresh_token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Optional<UserProfileDto>> register(@RequestBody User user) {
        if (user.getEmail() == null || !this.userService.existsByEmail(user.getEmail())) {

            String currentDate = String.valueOf(LocalDateTime.now());
            user.setRegisterDate(currentDate);
            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            this.userService.save(user);

            Optional<User> userDB = this.userService.findUserByEmail(user.getEmail());

            UserRole userRole = new UserRole();
            userRole.setUserId(userDB.get().getUserId());
            userRole.setRole("USER");
            userRole.setGrantedDate(currentDate);
            this.userRoleService.save(userRole);

            Category category = new Category();
            category.setUserId(userDB.get().getUserId());
            category.setCategoryName("Others");
            this.categoryService.save(category);

            Account account = new Account();
            account.setUserId(userDB.get().getUserId());
            account.setAccountName("Cash");
            account.setAvailableMoney(0.0);
            account.setAvailable(true);
            this.accountService.save(account);


            return ResponseEntity.ok(Optional.of(new UserProfileDto(
                            userDB.get().getUserId(),
                            userDB.get().getUsername(),
                            userDB.get().getEmail(),
                            userDB.get().getRegisterDate(),
                            userDB.get().getRoles()

                    ))
            );
        }
        return ResponseEntity.badRequest().build(); //TODO (Es necesario hace configurar la respuesta para que no se regrese la contraseña en la respuesta)

    }

    @PostMapping("/is-available")
    public ResponseEntity<ResponseIsAvailableDto> isAvailable(@RequestBody RequestEmailDto email) {
        if (!this.userService.existsByEmail(email.email())) {
            return ResponseEntity.ok(new ResponseIsAvailableDto(true));
        } else {
            return ResponseEntity.ok(new ResponseIsAvailableDto(false));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        int userId = Integer.parseInt(this.jwtUtil.getUsername(refreshTokenDto.refresh_token()));
        String access_token = this.jwtUtil.createJwt(userId);
        String refresh_token = this.jwtUtil.createJwtRefresh(userId);
        return ResponseEntity.ok(new TokenDto(access_token, refresh_token));
    }


    @PostMapping("/recovery")
    public ResponseEntity<RecoveryResponseDto> recovery(@RequestBody RequestEmailDto email) throws MessagingException {
        if (this.userService.existsByEmail(email.email())) {
            Optional<User> userDb = this.userService.findUserByEmail(email.email());
            int userId = userDb.get().getUserId();
            String token = this.jwtUtil.createJwtRecovery(userId);
            String link = "http://localhost:4200/auth/recovery?token=" + token;

            // Envío del correo electrónico
            this.mailManager.sendEmail(email.email(), "Password Recovery", link);

            return ResponseEntity.ok(new RecoveryResponseDto(link, token)); // TODO (Quitar envio de token en la respuesta del metodo)
        }
        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @PostMapping("/change-password")
    public ResponseEntity<MessageDto> changePassword(@RequestBody ChangePasswordDto dto) { //TODO (Complementar respuesta, ejm, cuando la new password sea igual a la contraseña existente, notificarlo, o que se pueada guardar un registro de contraseñas, para no poner una contraseña que ya hubiere estado en el registro)
        String emailUser = this.jwtUtil.getUsername(dto.token());
        String passwordEncoded = this.passwordEncoder.encode(dto.newPassword());
        if (this.userService.existsByEmail(emailUser) && this.jwtUtil.isValid(dto.token())) {
            if (this.userService.changePassword(emailUser, passwordEncoded)) {
                return ResponseEntity.ok(new MessageDto(true));
            } else {
                return ResponseEntity.ok((new MessageDto(false)));
            }
        } else {
            return ResponseEntity.ok((new MessageDto(false)));
        }
    }


}
