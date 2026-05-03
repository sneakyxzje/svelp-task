package com.API.api.infrastructure.security;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.API.api.application.port.TokenService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider implements TokenService {

    private final String secret;
    private final long accessTokenExpired;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpired) {
        this.secret = secret;
        this.accessTokenExpired = accessTokenExpired;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String userId, String identity) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenExpired);

        return Jwts.builder()
                .subject(userId)
                .claim("identity", identity)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload()
                .getSubject();
    }
}
