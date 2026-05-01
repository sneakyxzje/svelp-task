package com.API.api.presentation.dto;

public record LoginRequest(
        String identity,
        String password) {

}
