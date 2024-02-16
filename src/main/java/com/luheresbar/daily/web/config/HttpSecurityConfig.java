package com.luheresbar.daily.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true) // esta anotacion se usa para controlar la anotacion @Secured que esta sobre un metodo en un servicio.
public class HttpSecurityConfig {

    private final JwtFilter jwtFilter;

    public HttpSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // el codigo siguiente indica que todas las peticiones deben estar autenticadas con httpBasic.
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // mi aplicacion será un API STATELESS: no almacea estados, no almacena sessiones
                .authorizeHttpRequests(customizeRequests -> {
                    customizeRequests
                            .requestMatchers(HttpMethod.POST, "api/auth/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN") // los roles ADMIN y CUSTOMER puede ejecutar peticiones GET en esa direccion.
//                            .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN") // solamente el rol ADMIN puede ejecutar peticiones POST en esa direccion.
//                            .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                            .requestMatchers("/api/orders/**").hasRole("ADMIN") // todos los metodos (crud) de esta direccion solo seran manipulables por el rol ADMIN.
                            .anyRequest()
                            .authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Indica que estamos incluyenbdo el friltro que creamos dentro de la cadena de seguridad de spring, antes del filtro basicauth
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


    // Bean para implementar el controller de login: AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Se creó el ProviderManager que implementa AuthenticationManager
    }
}

