package com.API.api.application.usecase.task;

import com.API.api.application.command.CreateTaskCommand;
import com.API.api.application.service.ColumnPermissionService;
import com.API.api.domain.model.Column;
import com.API.api.domain.model.Task;
import com.API.api.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTaskUseCase {
    private final TaskRepository taskRepository;
    private final ColumnPermissionService columnPermissionService;

    public Task createTask(CreateTaskCommand command, Long userId) {
        Column column = columnPermissionService.hasPermission(userId, command.columnId());

        Double maxPos = taskRepository.findMaxPositionByColumnId(column.getId());
        Double newPos = (maxPos == null) ? 1000.0 : maxPos + 1000.0;

        Task task = new Task(
                null,
                command.title(),
                null,
                column.getId(),
                newPos,
                null,
                null,
                null,
                null,
                null);
        return taskRepository.save(task);
    }
}
