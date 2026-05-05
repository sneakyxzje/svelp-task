package com.API.api.application.command;

import com.API.api.domain.enums.Priority;
import java.time.LocalDateTime;

public record CreateTaskCommand(
    String title,
    String description,
    Long columnId,
    Priority priority,
    Long assigneeId,
    LocalDateTime dueDate
) {}
