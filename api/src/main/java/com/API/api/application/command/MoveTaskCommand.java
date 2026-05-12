package com.API.api.application.command;

public record MoveTaskCommand(
        Long targetColumnId,
        Double prevTaskPosition,
        Double nextTaskPosition) {

}
