package com.API.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.API.api.application.command.CreateBoardCommand;
import com.API.api.application.usecase.board.CreateBoardUseCase;
import com.API.api.application.usecase.board.GetBoardByWorkspaceUseCase;
import com.API.api.domain.model.Board;
import com.API.api.infrastructure.security.UserPrincipal;
import com.API.api.presentation.dto.BoardRequest;
import com.API.api.presentation.dto.BoardResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final CreateBoardUseCase createBoardUseCase;
    private final GetBoardByWorkspaceUseCase getBoardByWorkspaceUseCase;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CreateBoardCommand command = new CreateBoardCommand(
                request.name(),
                request.workspaceId(),
                request.slug());
        Board created = createBoardUseCase.createBoard(command, userPrincipal.getId());
        return ResponseEntity.status(201).body(new BoardResponse(
                created.getId(),
                created.getName(),
                created.getWorkspaceId(),
                created.getSlug()));
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoardsByWorkspaceId(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(getBoardByWorkspaceUseCase.getBoardsByWorkspaceId(workspaceId).stream()
                .map(board -> new BoardResponse(
                        board.getId(),
                        board.getName(),
                        board.getWorkspaceId(),
                        board.getSlug()))
                .toList());
    }
}
