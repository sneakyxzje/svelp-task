package com.API.api.infrastructure.persistence.mapper;

import com.API.api.domain.model.Workspace;
import com.API.api.infrastructure.persistence.entity.UserEntity;
import com.API.api.infrastructure.persistence.entity.WorkspaceEntity;

public class WorkspaceMapper {
    public static Workspace toDomain(WorkspaceEntity entity) {
        return new Workspace(
                entity.getId(),
                entity.getName(),
                entity.getOwner().getId(),
                entity.getDescription(),
                entity.getSlug(),
                entity.getIcon(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static WorkspaceEntity toEntity(Workspace workspace) {
        WorkspaceEntity entity = new WorkspaceEntity();
        entity.setId(workspace.getId());
        entity.setName(workspace.getName());
        entity.setDescription(workspace.getDescription());
        entity.setSlug(workspace.getSlug());
        entity.setIcon(workspace.getIcon());
        if (workspace.getOwner() != null) {
            UserEntity ownerEntity = new UserEntity();
            ownerEntity.setId(workspace.getOwner());
            entity.setOwner(ownerEntity);
        }
        return entity;
    }
}
