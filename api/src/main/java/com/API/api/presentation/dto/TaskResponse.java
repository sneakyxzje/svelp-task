package com.API.api.presentation.dto;

import com.API.api.domain.enums.Priority;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
