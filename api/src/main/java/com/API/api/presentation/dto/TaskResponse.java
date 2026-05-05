package com.API.api.presentation.dto;

import com.API.api.domain.enums.Priority;
import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    Long columnId,
    Double position,
    Priority priority,
    Long assigneeId,
    LocalDateTime dueDate,
    LocalDateTime createdAt
) {}
