package com.tutor.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    // ⚠ MUST be identical in JwtValidator
    private static final String JWT_SECRET =
        "tutorcrm_super_secure_secret_key_for_hs512_algorithm_minimum_64_characters_long_2026";

    private final SecretKey key =
        Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    // ✅ GENERATE TOKEN
    public String generateToken(Long userId, String userName, String role) {
        return Jwts.builder()
                .subject(userName)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    // ✅ GET USERNAME
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ✅ GET CLAIMS
    public Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}