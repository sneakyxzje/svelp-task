package com.API.api.application.command;

import java.time.LocalDateTime;

public record UpdateTaskCommand(
    Long id,
    String title,
    String description,
    String priority,
    Long assigneeId,
    LocalDateTime dueDate
) {}
