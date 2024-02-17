package com.luheresbar.daily.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // corsConfiguration.setAllowedOrigins(Arrays.asList("*")); // En este momento estamos permitiendo todas las peticiones que lleguen desde este origen.
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "URL: https://dailyexpenses-dev.web.app")); // En este momento estamos permitiendo todas las peticiones que lleguen desde este origen.
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // De esta forma se deja claro que metodos quiero que se consuman desde un origen cruzado.
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Esta es una configuracion mucho mas general, en donde se esta permitiendo todos lo encabezados que vengan a travez de cors.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
