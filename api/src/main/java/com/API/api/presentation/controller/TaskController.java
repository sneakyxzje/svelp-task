package com.API.api.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.API.api.application.command.CreateTaskCommand;
import com.API.api.application.command.MoveTaskCommand;
import com.API.api.application.command.UpdateTaskCommand;
import com.API.api.application.usecase.task.CreateTaskUseCase;
import com.API.api.application.usecase.task.MoveTaskUseCase;
import com.API.api.application.usecase.task.UpdateTaskUseCase;
import com.API.api.domain.model.Task;
import com.API.api.infrastructure.security.UserPrincipal;
import com.API.api.presentation.dto.MoveTaskRequest;
import com.API.api.presentation.dto.TaskRequest;
import com.API.api.presentation.dto.TaskResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
        private final CreateTaskUseCase createTaskUseCase;
        private final UpdateTaskUseCase updateTaskUseCase;
        private final MoveTaskUseCase moveTaskUseCase;

        @PostMapping
        public ResponseEntity<TaskResponse> createTask(
                        @RequestBody TaskRequest request,
                        @AuthenticationPrincipal UserPrincipal principal) {

                CreateTaskCommand command = new CreateTaskCommand(
                                request.title(),
                                request.columnId());

                Task created = createTaskUseCase.createTask(command, principal.getId());

                return ResponseEntity.status(201).body(mapToResponse(created));
        }

        @PatchMapping("/{id}")
        public ResponseEntity<TaskResponse> updateTask(
                        @PathVariable Long id,
                        @RequestBody TaskRequest request,
                        @AuthenticationPrincipal UserPrincipal principal) {

                UpdateTaskCommand command = new UpdateTaskCommand(
                                id,
                                request.title(),
                                request.description(),
                                request.priority() != null ? request.priority().name() : null,
                                request.assigneeId(),
                                request.dueDate());

                Task updated = updateTaskUseCase.updateTask(command, principal.getId());

                return ResponseEntity.ok(mapToResponse(updated));
        }

        private TaskResponse mapToResponse(Task task) {
                return new TaskResponse(
                                task.getId(),
                                task.getTitle(),
                                task.getDescription(),
                                task.getColumnId(),
                                task.getPosition(),
                                task.getPriority(),
                                task.getAssigneeId(),
                                task.getDueDate(),
                                task.getCreatedAt());
        }

        @PatchMapping("/{id}/move")
        public ResponseEntity<TaskResponse> moveTask(@RequestBody MoveTaskRequest request, @PathVariable Long id,
                        @AuthenticationPrincipal UserPrincipal principal) {
                MoveTaskCommand command = new MoveTaskCommand(
                                request.targetColumnId(),
                                request.prevTaskPosition(),
                                request.nextTaskPosition());
                Task moved = moveTaskUseCase.moveTask(command, id, principal.getId());
                return ResponseEntity.ok(mapToResponse(moved));
        }
}
