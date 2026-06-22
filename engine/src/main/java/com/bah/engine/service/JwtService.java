package com.bah.engine.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final byte[] secretKey;
    private final long expirationTime;

    public JwtService(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-time}") long expirationTime) {
        this.secretKey = secretKey.getBytes(StandardCharsets.UTF_8);
        this.expirationTime = expirationTime;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey))
                .compact();
    }

    public boolean isTokenValid(String token) {
        Claims claims = parseAllClaims(token);
        Date expiration = claims.getExpiration();
        return expiration != null && expiration.after(new Date());
    }

    public String extractSubject(String token) {
        return parseAllClaims(token).getSubject();
    }

    private Claims parseAllClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey))
                .build()
                .parse(token)
                .getPayload();
    }

}
