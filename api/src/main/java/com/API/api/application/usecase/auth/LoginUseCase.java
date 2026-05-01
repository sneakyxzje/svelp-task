package com.API.api.application.usecase.auth;

import com.API.api.application.command.LoginCommand;
import com.API.api.application.port.PasswordEncode;
import com.API.api.application.port.TokenService;
import com.API.api.domain.model.User;
import com.API.api.domain.repository.UserRepository;

public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncode passwordEncode;
    private final TokenService tokenService;

    public LoginUseCase(UserRepository userRepository, PasswordEncode passwordEncode, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncode = passwordEncode;
        this.tokenService = tokenService;
    }

    public String login(LoginCommand command) {
        User user = userRepository.findByEmailOrUsername(command.identity())
                .orElseThrow(() -> new RuntimeException("Email or username not exists"));
        if (!passwordEncode.matches(command.password(), user.getPassword())) {
            throw new RuntimeException("Email or username is not exists");
        }
        String userId = String.valueOf(user.getId());
        String token = tokenService.generateAccessToken(userId, command.identity());
        return token;
    }
}
