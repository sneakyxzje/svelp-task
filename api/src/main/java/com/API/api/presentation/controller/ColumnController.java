package com.API.api.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.api.application.command.CreateColumnCommand;
import com.API.api.application.usecase.column.CreateColumnUseCase;
import com.API.api.domain.model.Column;
import com.API.api.infrastructure.security.UserPrincipal;
import com.API.api.presentation.dto.ColumnRequest;
import com.API.api.presentation.dto.ColumnResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final CreateColumnUseCase createColumnUseCase;

    @PostMapping
    public ResponseEntity<ColumnResponse> createColumn(@RequestBody ColumnRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        CreateColumnCommand command = new CreateColumnCommand(
                request.name(),
                request.boardId());
        Column created = createColumnUseCase.createColumn(command, principal.getId());
        return ResponseEntity.status(201).body(new ColumnResponse(
                created.getId(),
                created.getName(),
                created.getPosition()));
    }

}
