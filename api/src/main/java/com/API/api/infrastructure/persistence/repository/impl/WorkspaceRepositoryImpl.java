package com.API.api.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.API.api.domain.model.Workspace;
import com.API.api.domain.repository.WorkspaceRepository;
import com.API.api.infrastructure.persistence.entity.WorkspaceEntity;
import com.API.api.infrastructure.persistence.mapper.WorkspaceMapper;
import com.API.api.infrastructure.persistence.repository.JpaWorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepositoryImpl implements WorkspaceRepository {
    private final JpaWorkspaceRepository jpaWorkspaceRepository;

    @Override
    public Workspace save(Workspace workspace) {
        WorkspaceEntity entity = WorkspaceMapper.toEntity(workspace);
        WorkspaceEntity saved = jpaWorkspaceRepository.save(entity);
        return WorkspaceMapper.toDomain(saved);
    }

    @Override
    public Optional<Workspace> findById(Long id) {
        Optional<WorkspaceEntity> entity = jpaWorkspaceRepository.findById(id);
        return entity.map(WorkspaceMapper::toDomain);
    }

    public List<Workspace> findByOwnerId(Long id) {
        List<WorkspaceEntity> entities = jpaWorkspaceRepository.findByOwnerId(id);
        return entities.stream().map(WorkspaceMapper::toDomain).toList();
    }

    public Optional<Workspace> findBySlug(String slug) {
        Optional<WorkspaceEntity> entity = jpaWorkspaceRepository.findBySlug(slug);
        return entity.map(WorkspaceMapper::toDomain);
    }

    public boolean existsBySlugAndOwnerId(String slug, Long ownerId) {
        return jpaWorkspaceRepository.existsBySlugAndOwnerId(slug, ownerId);
    }
}
