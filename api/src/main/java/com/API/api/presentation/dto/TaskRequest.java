package com.API.api.presentation.dto;

import com.API.api.domain.enums.Priority;
import java.time.LocalDateTime;

public record TaskRequest(
    String title,
    String description,
    Long columnId,
    Priority priority,
    Long assigneeId,
    LocalDateTime dueDate
) {}
