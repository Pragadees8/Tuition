 package com.tutor.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtValidator extends OncePerRequestFilter {

    // ⚠ MUST be identical to JwtProvider
    private static final String JWT_SECRET =
        "tutorcrm_super_secure_secret_key_for_hs512_algorithm_minimum_64_characters_long_2026";

    private final SecretKey key =
        Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractToken(request);

        try {
            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                Long userId = claims.get("userId", Long.class);
                String role = String.valueOf(claims.get("role"));

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(role)
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            System.out.println("JWT Validation Error → " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    // ✅ CLEAN TOKEN EXTRACTION
    private String extractToken(HttpServletRequest request) {

        // From Cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // From Authorization Header
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("JWT from Authorization Header = " + token);
            return token;
        }

        return null;
    }
}