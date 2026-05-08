package com.API.api.domain.model;

import java.time.LocalDateTime;

import com.API.api.domain.enums.Priority;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long columnId;
    private Double position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Priority priority;
    private Long assigneeId;
    private LocalDateTime dueDate;

    public Task(Long id, String title, String description, Long columnId, Double position, LocalDateTime createdAt,
            LocalDateTime updatedAt, Priority priority, Long assigneeId, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.columnId = columnId;
        this.position = position;
        this.priority = priority;
        this.assigneeId = assigneeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getColumnId() {
        return this.columnId;
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

    public Priority getPriority() {
        return this.priority;
    }

    public Long getAssigneeId() {
        return this.assigneeId;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
