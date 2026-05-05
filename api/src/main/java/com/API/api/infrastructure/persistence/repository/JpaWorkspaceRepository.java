package com.API.api.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.api.infrastructure.persistence.entity.WorkspaceEntity;

public interface JpaWorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {

    Optional<WorkspaceEntity> findById(Long id);

    List<WorkspaceEntity> findByOwnerId(Long id);

    Optional<WorkspaceEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
