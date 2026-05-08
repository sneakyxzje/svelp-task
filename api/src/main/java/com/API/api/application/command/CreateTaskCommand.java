package com.API.api.application.command;

public record CreateTaskCommand(
    String title,
    Long columnId
) {}
