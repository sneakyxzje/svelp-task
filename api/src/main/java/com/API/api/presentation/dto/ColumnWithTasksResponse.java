package com.API.api.presentation.dto;

import java.util.List;

public record ColumnWithTasksResponse(
        Long id,
        String name,
        Double position,
        List<TaskResponse> tasks) {

}
