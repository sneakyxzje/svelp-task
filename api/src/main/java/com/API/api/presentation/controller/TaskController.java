package com.API.api.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.API.api.application.command.CreateTaskCommand;
import com.API.api.application.usecase.task.CreateTaskUseCase;
import com.API.api.domain.model.Task;
import com.API.api.infrastructure.security.UserPrincipal;
import com.API.api.presentation.dto.TaskRequest;
import com.API.api.presentation.dto.TaskResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final CreateTaskUseCase createTaskUseCase;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        
        CreateTaskCommand command = new CreateTaskCommand(
                request.title(),
                request.description(),
                request.columnId(),
                request.priority(),
                request.assigneeId(),
                request.dueDate()
        );

        Task created = createTaskUseCase.createTask(command, principal.getId());
        
        return ResponseEntity.status(201).body(new TaskResponse(
                created.getId(),
                created.getTitle(),
                created.getDescription(),
                created.getColumnId(),
                created.getPosition(),
                created.getPriority(),
                created.getAssigneeId(),
                created.getDueDate(),
                created.getCreatedAt()
        ));
    }
}
