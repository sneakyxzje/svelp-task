package com.API.api.application.service;

import com.API.api.domain.model.Board;
import com.API.api.domain.repository.BoardRepository;

public class BoardPermissionService {
    private final BoardRepository boardRepository;
    private final WorkspacePermissionService workspacePermissionService;

    public BoardPermissionService(BoardRepository boardRepository,
            WorkspacePermissionService workspacePermissionService) {
        this.boardRepository = boardRepository;
        this.workspacePermissionService = workspacePermissionService;
    }

    public Board hasPermission(Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        workspacePermissionService.hasPermission(userId, board.getWorkspaceId());
        return board;
    }

}
