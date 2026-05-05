package com.API.api.domain.repository;

import java.util.List;
import java.util.Optional;
import com.API.api.domain.model.Task;

public interface TaskRepository {
    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findByColumnId(Long columnId);

    Double findMaxPositionByColumnId(Long columnId);

    List<Task> findAllByColumnIds(List<Long> columnIds);
}
