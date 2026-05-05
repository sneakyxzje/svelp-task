package com.API.api.infrastructure.persistence.mapper;

import com.API.api.domain.model.Column;
import com.API.api.infrastructure.persistence.entity.BoardEntity;
import com.API.api.infrastructure.persistence.entity.ColumnEntity;

public class ColumnMapper {
    public static Column toDomain(ColumnEntity columnEntity) {
        return new Column(
                columnEntity.getId(),
                columnEntity.getName(),
                columnEntity.getBoard().getId(),
                columnEntity.getPosition(),
                columnEntity.getCreatedAt(),
                columnEntity.getUpdatedAt());
    }

    public static ColumnEntity toEntity(Column column) {
        ColumnEntity entity = new ColumnEntity();
        entity.setId(column.getId());
        entity.setName(column.getName());
        if (column.getBoardId() != null) {
            BoardEntity boardEntity = new BoardEntity();
            boardEntity.setId(column.getBoardId());
            entity.setBoard(boardEntity);
        }
        entity.setPosition(column.getPosition());
        entity.setCreatedAt(column.getCreatedAt());
        entity.setUpdatedAt(column.getUpdatedAt());
        return entity;
    }
}
