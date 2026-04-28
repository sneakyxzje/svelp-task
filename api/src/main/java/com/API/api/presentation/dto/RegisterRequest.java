package com.API.api.presentation.dto;

public record RegisterRequest(
                String fullname,
                String username,
                String email,
                String password) {

}
