package com.API.api.application.command;

public record RegisterCommand(
                String fullname,
                String username,
                String email,
                String password) {
}
