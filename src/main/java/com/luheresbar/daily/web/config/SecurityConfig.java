package com.luheresbar.daily.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // el codigo siguiente indica que todas las peticiones deben estar autenticadas con httpBasic.
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(customizeRequests -> {
                    customizeRequests
                            .requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN") // los roles ADMIN y CUSTOMER puede ejecutar peticiones GET en esa direccion.
//                            .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN") // solamente el rol ADMIN puede ejecutar peticiones POST en esa direccion.
//                            .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                            .requestMatchers("/api/orders/**").hasRole("ADMIN") // todos los metodos (crud) de esta direccion solo seran manipulables por el rol ADMIN.
                            .anyRequest()
                            .authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public UserDetailsService memoryUsers() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails customer = User.builder()
//                .username("customer")
//                .password(passwordEncoder().encode("customer123"))
//                .roles("CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, customer);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

