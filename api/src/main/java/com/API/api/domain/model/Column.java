package com.API.api.domain.model;

import java.time.LocalDateTime;

public class Column {

    private Long id;
    private String name;
    private Long boardId;
    private Double position;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Column(Long id, String name, Long boardId, Double position, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;
        this.position = position;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getBoardId() {
        return this.boardId;
    }

    public Double getPosition() {
        return this.position;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
