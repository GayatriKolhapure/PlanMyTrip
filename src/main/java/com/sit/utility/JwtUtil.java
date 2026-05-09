package com.sit.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    // 🔴 IMPORTANT: keep secret fixed and strong (at least 32 chars)
    private static final String SECRET = "my-super-secret-key-my-super-secret-key-123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 🔐 Generate Token
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)   // ✅ store role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                .signWith(key)
                .compact();
    }

    // 📥 Extract Role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // 📥 Extract Email
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // 📥 Extract Claims (safe parsing)
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String email) {

        final String extractedEmail = extractEmail(token);

        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // ⏳ Check Expiry
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}