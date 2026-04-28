package com.API.api.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.API.api.application.port.PasswordEncode;
import com.API.api.application.usecase.auth.RegisterUseCase;
import com.API.api.domain.repository.UserRepository;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RegisterUseCase registerUseCase(UserRepository repo, PasswordEncode encode) {
        return new RegisterUseCase(repo, encode);
    }
}
