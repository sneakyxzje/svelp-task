package com.API.api.domain.model;

import java.time.LocalDateTime;

public class Board {

    private Long id;
    private String name;
    private Long workspaceId;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Board(Long id, String name, Long workspaceId, String slug, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.workspaceId = workspaceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getWorkspaceId() {
        return this.workspaceId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public String getSlug() {
        return this.slug;
    }
}
