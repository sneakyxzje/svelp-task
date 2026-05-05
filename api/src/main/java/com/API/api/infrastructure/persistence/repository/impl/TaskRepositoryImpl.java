package com.API.api.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.API.api.domain.model.Task;
import com.API.api.domain.repository.TaskRepository;
import com.API.api.infrastructure.persistence.entity.TaskEntity;
import com.API.api.infrastructure.persistence.mapper.TaskMapper;
import com.API.api.infrastructure.persistence.repository.JpaTaskRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final JpaTaskRepository jpaTaskRepository;

    @Override
    public Task save(Task task) {
        TaskEntity entity = TaskMapper.toEntity(task);
        TaskEntity saved = jpaTaskRepository.save(entity);
        return TaskMapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id).map(TaskMapper::toDomain);
    }

    @Override
    public List<Task> findByColumnId(Long columnId) {
        return jpaTaskRepository.findByColumnIdOrderByPositionAsc(columnId)
                .stream()
                .map(TaskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Double findMaxPositionByColumnId(Long columnId) {
        return jpaTaskRepository.findMaxPositionByColumnId(columnId);
    }

    @Override
    public List<Task> findAllByColumnIds(List<Long> columnIds) {
        return jpaTaskRepository.findAllByColumnIdInOrderByPositionAsc(columnIds).stream().map(TaskMapper::toDomain)
                .collect(Collectors.toList());
    }
}
