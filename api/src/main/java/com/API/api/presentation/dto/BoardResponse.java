package com.API.api.presentation.dto;

public record BoardResponse(
                Long id,
                String name,
                Long workspaceId,
                String slug) {

}
