package com.API.api.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MoveTaskRequest(
        Long targetColumnId,
        Double prevTaskPosition,
        Double nextTaskPosition) {

}
