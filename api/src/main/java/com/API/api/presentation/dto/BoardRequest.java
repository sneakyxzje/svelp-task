package com.API.api.presentation.dto;

public record BoardRequest(
                String name,
                Long workspaceId,
                String slug,
                String description) {

}
