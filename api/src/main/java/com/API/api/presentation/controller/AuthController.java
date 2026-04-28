package com.API.api.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.api.application.command.RegisterCommand;
import com.API.api.application.usecase.auth.RegisterUseCase;
import com.API.api.presentation.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        registerUseCase.register(new RegisterCommand(
                request.fullname(),
                request.username(),
                request.email(),
                request.password()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
