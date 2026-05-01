package com.API.api.infrastructure.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.API.api.application.port.PasswordEncode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncode {
    private final PasswordEncoder encoder;

    public String encode(String password) {
        return encoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodePassword) {
        return encoder.matches(rawPassword, encodePassword);
    }
}
