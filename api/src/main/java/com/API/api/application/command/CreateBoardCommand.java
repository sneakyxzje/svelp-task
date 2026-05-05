package com.API.api.application.command;

public record CreateBoardCommand(
                String name,
                Long workspaceId,
                String slug) {

}
