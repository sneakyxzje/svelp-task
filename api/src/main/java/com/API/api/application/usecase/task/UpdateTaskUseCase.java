package com.API.api.application.usecase.task;

import java.time.LocalDateTime;

import com.API.api.application.command.UpdateTaskCommand;
import com.API.api.application.service.ColumnPermissionService;
import com.API.api.domain.enums.Priority;
import com.API.api.domain.model.Task;
import com.API.api.domain.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateTaskUseCase {
    private final TaskRepository taskRepository;
    private final ColumnPermissionService columnPermissionService;

    public Task updateTask(UpdateTaskCommand command, Long userId) {
        Task task = taskRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Kiểm tra quyền của người dùng đối với Column chứa Task này
        columnPermissionService.hasPermission(userId, task.getColumnId());

        if (command.title() != null) {
            task.setTitle(command.title());
        }
        if (command.description() != null) {
            task.setDescription(command.description());
        }
        if (command.priority() != null) {
            task.setPriority(Priority.valueOf(command.priority().toUpperCase()));
        }
        if (command.assigneeId() != null) {
            task.setAssigneeId(command.assigneeId());
        }
        if (command.dueDate() != null) {
            task.setDueDate(command.dueDate());
        }

        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
}
