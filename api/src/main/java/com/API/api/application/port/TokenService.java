package com.API.api.application.port;

public interface TokenService {
    String generateAccessToken(String userId, String identity);

    boolean validateToken(String token);

    String getUserIdFromToken(String token);
}
