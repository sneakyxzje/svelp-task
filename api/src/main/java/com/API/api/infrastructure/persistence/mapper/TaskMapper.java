package com.API.api.infrastructure.persistence.mapper;

import com.API.api.domain.model.Task;
import com.API.api.infrastructure.persistence.entity.ColumnEntity;
import com.API.api.infrastructure.persistence.entity.TaskEntity;

public class TaskMapper {
    public static Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getColumn().getId(),
                entity.getPosition(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getPriority(),
                entity.getAssigneeId(),
                entity.getDueDate()
        );
    }

    public static TaskEntity toEntity(Task domain) {
        TaskEntity entity = new TaskEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        
        if (domain.getColumnId() != null) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setId(domain.getColumnId());
            entity.setColumn(columnEntity);
        }
        
        entity.setPosition(domain.getPosition());
        entity.setPriority(domain.getPriority());
        entity.setAssigneeId(domain.getAssigneeId());
        entity.setDueDate(domain.getDueDate());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        
        return entity;
    }
}
