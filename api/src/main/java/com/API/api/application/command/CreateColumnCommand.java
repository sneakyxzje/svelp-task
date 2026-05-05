package com.API.api.application.command;

public record CreateColumnCommand(
        String name,
        Long boardId) {

}
