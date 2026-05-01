package com.API.api.application.command;

public record LoginCommand(
                String identity,
                String password) {
}
