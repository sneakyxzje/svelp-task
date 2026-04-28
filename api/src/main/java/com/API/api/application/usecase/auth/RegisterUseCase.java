package com.API.api.application.usecase.auth;

import com.API.api.application.command.RegisterCommand;
import com.API.api.application.port.PasswordEncode;
import com.API.api.domain.enums.Role;
import com.API.api.domain.model.User;
import com.API.api.domain.repository.UserRepository;

public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncode passwordEncode;

    public RegisterUseCase(UserRepository userRepository, PasswordEncode passwordEncode) {
        this.userRepository = userRepository;
        this.passwordEncode = passwordEncode;
    }

    public void register(RegisterCommand commands) {
        if (userRepository.existsByUsername(commands.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(commands.email())) {
            throw new RuntimeException("Email already exists");
        }
        String hashed = passwordEncode.encode(commands.password());
        User user = new User(null, commands.fullname(), commands.username(), commands.email(), hashed,
                Role.USER);
        userRepository.save(user);
    }
}
