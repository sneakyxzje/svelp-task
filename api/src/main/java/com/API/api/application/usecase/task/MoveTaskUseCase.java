package com.API.api.application.usecase.task;

import com.API.api.application.command.MoveTaskCommand;
import com.API.api.application.port.TransactionManager;
import com.API.api.application.service.ColumnPermissionService;
import com.API.api.domain.model.Task;
import com.API.api.domain.repository.TaskRepository;

public class MoveTaskUseCase {
    private final TaskRepository taskRepository;
    private final ColumnPermissionService columnPermissionService;
    private final TransactionManager transactionManager;

    public MoveTaskUseCase(TaskRepository taskRepository, ColumnPermissionService columnPermissionService,
            TransactionManager transactionManager) {
        this.taskRepository = taskRepository;
        this.columnPermissionService = columnPermissionService;
        this.transactionManager = transactionManager;
    }

    public Task moveTask(MoveTaskCommand command, Long taskId, Long userId) {
        return transactionManager.execute(() -> {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            columnPermissionService.hasPermission(userId, task.getColumnId());
            columnPermissionService.hasPermission(userId, command.targetColumnId());
            Double newPos;
            Double prev = command.prevTaskPosition();

            Double next = command.nextTaskPosition();

            if (prev != null && next != null) {
                newPos = (prev + next) / 2.0;
            } else if (prev != null) {
                newPos = prev + 1024.0;
            } else if (next != null) {
                newPos = next / 2.0;
            } else {
                newPos = 1024.0;
            }

            task.setColumnId(command.targetColumnId());
            task.setPosition(newPos);
            return taskRepository.save(task);
        });
    }
}
