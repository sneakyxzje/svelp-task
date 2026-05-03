package com.API.api.domain.model;

import java.time.LocalDateTime;

public class Workspace {
    private Long id;
    private String name;
    private Long owner;
    private String description;
    private String slug;
    private String icon;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Workspace(Long id, String name, Long owner, String description, String slug, String icon,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.slug = slug;
        this.icon = icon;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getOwner() {
        return this.owner;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSlug() {
        return this.slug;
    }

    public String getIcon() {
        return this.icon;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
