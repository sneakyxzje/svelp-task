package com.API.api.application.port;

public interface TokenService {
    String generateAccessToken(String userId, String identity);
}
