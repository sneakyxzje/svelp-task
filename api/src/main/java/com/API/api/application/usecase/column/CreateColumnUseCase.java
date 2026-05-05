package com.API.api.application.usecase.column;

import java.time.LocalDateTime;

import com.API.api.application.command.CreateColumnCommand;
import com.API.api.application.service.BoardPermissionService;
import com.API.api.domain.model.Board;
import com.API.api.domain.model.Column;
import com.API.api.domain.repository.ColumnRepository;

public class CreateColumnUseCase {

    private final ColumnRepository columnRepository;
    private final BoardPermissionService boardPermissionService;

    public CreateColumnUseCase(ColumnRepository columnRepository, BoardPermissionService boardPermissionService) {
        this.columnRepository = columnRepository;
        this.boardPermissionService = boardPermissionService;
    }

    public Column createColumn(CreateColumnCommand command, Long userId) {
        Board board = boardPermissionService.hasPermission(userId, command.boardId());
        Double maxPos = getMaxPosition(board.getId());
        Double newPos = (maxPos == null) ? 1000.0 : maxPos + 1000.0;
        Column column = new Column(
                null,
                command.name(),
                command.boardId(),
                newPos,
                LocalDateTime.now(),
                LocalDateTime.now());
        return columnRepository.save(column);
    }

    private Double getMaxPosition(Long boardId) {
        return columnRepository.findMaxPositionByBoardId(boardId);
    }

}
