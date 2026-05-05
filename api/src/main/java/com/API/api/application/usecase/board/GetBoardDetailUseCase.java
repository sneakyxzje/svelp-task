package com.API.api.application.usecase.board;

import java.util.List;

import com.API.api.application.command.BoardDetailCommand;
import com.API.api.application.service.BoardPermissionService;
import com.API.api.domain.model.Board;
import com.API.api.domain.model.Column;
import com.API.api.domain.model.Task;
import com.API.api.domain.repository.BoardRepository;
import com.API.api.domain.repository.ColumnRepository;
import com.API.api.domain.repository.TaskRepository;

public class GetBoardDetailUseCase {
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final TaskRepository taskRepository;
    private final BoardPermissionService boardPermissionService;

    public GetBoardDetailUseCase(BoardRepository boardRepository, ColumnRepository columnRepository,
            TaskRepository taskRepository, BoardPermissionService boardPermissionService) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
        this.taskRepository = taskRepository;
        this.boardPermissionService = boardPermissionService;
    }

    public BoardDetailCommand getBoardDetails(String workspaceSlug, String boardSlug, Long userId) {
        Board board = boardRepository.findBySlugAndWorkspaceSlug(boardSlug, workspaceSlug)
                .orElseThrow(() -> new RuntimeException(
                        "Board not found with slug: " + boardSlug + " in workspace: " + workspaceSlug));

        boardPermissionService.hasPermission(userId, board.getId());

        List<Column> columns = columnRepository.findAllByBoardId(board.getId());
        List<Task> tasks = taskRepository.findAllByColumnIds(columns.stream().map(Column::getId).toList());
        return new BoardDetailCommand(board, columns, tasks);
    }
}
