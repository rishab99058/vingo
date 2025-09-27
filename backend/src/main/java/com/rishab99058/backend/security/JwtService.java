package com.rishab99058.backend.security;

import com.rishab99058.backend.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private SecretKey getSecretKey() {
       return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(UserEntity user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(user.getId())
                .claim("email", user.getEmail())
                .claim("roles", List.of(user.getRole()))
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60 * 24))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUserIdFromJwtToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getSubject();

    }

    public String getEmailFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }


}
