package com.API.api.application.usecase.board;

import java.util.List;

import com.API.api.domain.model.Board;
import com.API.api.domain.repository.BoardRepository;

public class GetBoardByWorkspaceUseCase {
    private final BoardRepository boardRepository;

    public GetBoardByWorkspaceUseCase(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoardsByWorkspaceId(Long workspaceId) {
        return boardRepository.findAllByWorkspaceId(workspaceId);
    }
}
