package com.API.api.application.usecase.board;

import java.time.LocalDateTime;

import com.API.api.application.command.CreateBoardCommand;
import com.API.api.application.service.WorkspacePermissionService;
import com.API.api.domain.model.Board;
import com.API.api.domain.repository.BoardRepository;

public class CreateBoardUseCase {
    private final BoardRepository boardRepository;
    private final WorkspacePermissionService workspacePermissionService;

    public CreateBoardUseCase(BoardRepository boardRepository, WorkspacePermissionService workspacePermissionService) {
        this.boardRepository = boardRepository;
        this.workspacePermissionService = workspacePermissionService;
    }

    public Board createBoard(CreateBoardCommand command, Long userId) {
        workspacePermissionService.hasPermission(userId, command.workspaceId());
        if (boardRepository.findByName(command.name()).isPresent()) {
            throw new RuntimeException("Board with name " + command.name() + " already exists");
        }
        Board board = new Board(
                null,
                command.name(),
                command.workspaceId(),
                command.slug(), LocalDateTime.now(), LocalDateTime.now());
        return boardRepository.save(board);
    }
}
