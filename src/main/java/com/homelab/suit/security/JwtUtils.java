package com.homelab.suit.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.security.jwt.secret}")
    private String jwtSecret;
    @Value("${app.security.jwt.expiration}")
    private int jwtExpirationMs;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(Authentication authentication) {

        // 1. Дістаємо email (це наш subject)
        String email = authentication.getName();

        // 2. Дістаємо роль зі Spring Security
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        // 3. Будуємо токен вашим новим синтаксисом
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()                    // parserBuilder() видалено, тепер parser() повертає білдер
                .verifyWith(getSigningKey())    // жорстка вимога безпеки у нових версіях
                .build()
                .parseSignedClaims(token)       // раніше було parseClaimsJws()
                .getPayload()                   // раніше було getBody()
                .getSubject();
    }

    // 4. Перевірка валідності токена
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);  // Якщо токен зламаний або протух, тут вилетить JwtException
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}