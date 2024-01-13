package com.luheresbar.daily.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "d41ly_3xp3ns3";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    public String create(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuer("daily-expenses")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) // Expiracion del token 15 Dias
                .sign(ALGORITHM);
    }

    // metodo que valida si un jwt es valio
    public boolean isValid(String jwt) {
        try {

        JWT.require(ALGORITHM)
                .build()
                .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Metodo para obtener el usuario al cual pertenece el jwt que se esta gestionando
    public  String getUsername(String jwt) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
