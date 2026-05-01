package com.API.api.application.port;

public interface PasswordEncode {
    String encode(String password);

    boolean matches(String rawPassword, String encodePassword);

}
