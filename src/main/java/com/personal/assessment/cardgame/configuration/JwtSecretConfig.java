package com.personal.assessment.cardgame.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretConfig {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Bean
    public SecretKey getSecretKey() {
        return jwtSecret;
    }
}
