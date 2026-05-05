package com.API.api.infrastructure.persistence.mapper;

import com.API.api.domain.model.Board;
import com.API.api.infrastructure.persistence.entity.BoardEntity;
import com.API.api.infrastructure.persistence.entity.WorkspaceEntity;

public class BoardMapper {
    public static Board toDomain(BoardEntity entity) {
        return new Board(
                entity.getId(),
                entity.getName(),
                entity.getWorkspace().getId(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static BoardEntity toEntity(Board board) {
        BoardEntity entity = new BoardEntity();
        entity.setId(board.getId());
        entity.setName(board.getName());
        entity.setSlug(board.getSlug());
        if (board.getWorkspaceId() != null) {
            WorkspaceEntity workspaceEntity = new WorkspaceEntity();
            workspaceEntity.setId(board.getWorkspaceId());
            entity.setWorkspace(workspaceEntity);
        }
        return entity;
    }
}
