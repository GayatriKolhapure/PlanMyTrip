package com.sit.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET = "my-super-secret-key-my-super-secret-key-123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔐 Generate Token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)   // ✅ ADD THIS
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }
    
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
    
    // 📥 Extract Email
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // 📥 Extract Claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email);
    }
}