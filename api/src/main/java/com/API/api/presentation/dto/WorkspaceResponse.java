package com.API.api.presentation.dto;

public record WorkspaceResponse(
                Long id,
                String name,
                Long owner,
                String description,
                String slug,
                String icon) {

}
