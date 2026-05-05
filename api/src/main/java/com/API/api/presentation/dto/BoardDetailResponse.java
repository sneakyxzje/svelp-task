package com.API.api.presentation.dto;

import java.util.List;

public record BoardDetailResponse(
        Long id,
        String name,
        List<ColumnWithTasksResponse> columns) {

}
