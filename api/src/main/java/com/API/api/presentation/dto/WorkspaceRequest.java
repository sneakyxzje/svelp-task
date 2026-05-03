package com.API.api.presentation.dto;

public record WorkspaceRequest(
                String name,
                String description,
                String slug,
                String icon) {

}
