package com.API.api.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.API.api.application.port.PasswordEncode;
import com.API.api.application.port.TokenService;
import com.API.api.application.usecase.auth.LoginUseCase;
import com.API.api.application.usecase.auth.RegisterUseCase;
import com.API.api.application.usecase.workspace.CreateWorkspaceUseCase;
import com.API.api.application.usecase.workspace.GetUserWorkspaceUseCase;
import com.API.api.domain.repository.UserRepository;
import com.API.api.domain.repository.WorkspaceRepository;

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

    @Bean
    public LoginUseCase loginUseCase(UserRepository userRepository, PasswordEncode encode, TokenService tokenService) {
        return new LoginUseCase(userRepository, encode, tokenService);
    }

    @Bean
    public CreateWorkspaceUseCase createWorkspaceUseCase(WorkspaceRepository repo) {
        return new CreateWorkspaceUseCase(repo);
    }

    @Bean
    public GetUserWorkspaceUseCase getUserWorkspaceUseCase(WorkspaceRepository repo) {
        return new GetUserWorkspaceUseCase(repo);
    }
}
