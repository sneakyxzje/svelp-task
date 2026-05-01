package com.API.api.presentation.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.api.application.command.LoginCommand;
import com.API.api.application.command.RegisterCommand;
import com.API.api.application.usecase.auth.LoginUseCase;
import com.API.api.application.usecase.auth.RegisterUseCase;
import com.API.api.presentation.dto.LoginRequest;
import com.API.api.presentation.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
        private final RegisterUseCase registerUseCase;
        private final LoginUseCase loginUseCase;

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
                registerUseCase.register(new RegisterCommand(
                                request.fullname(),
                                request.username(),
                                request.email(),
                                request.password()));
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest request) {
                String result = loginUseCase.login(new LoginCommand(
                                request.identity(),
                                request.password()));

                ResponseCookie cookie = ResponseCookie.from("accessToken", result)
                                .httpOnly(true)
                                .secure(false)
                                .path("/")
                                .maxAge(86400)
                                .sameSite("Strict")
                                .build();

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                                .body(Map.of("message", "Login successful"));
        }
}
