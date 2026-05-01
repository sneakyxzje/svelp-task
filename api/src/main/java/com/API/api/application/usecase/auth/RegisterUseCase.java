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

    public void register(RegisterCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
        }
        String hashed = passwordEncode.encode(command.password());
        User user = new User(null, command.fullname(), command.username(), command.email(), hashed,
                Role.USER);
        userRepository.save(user);
    }
}
