package com.API.api.application.command;

public record CreateWorkspaceCommand(
        String name,
        Long owner,
        String description,
        String slug,
        String icon) {

}
