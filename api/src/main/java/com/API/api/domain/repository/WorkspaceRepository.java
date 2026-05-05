package com.API.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.API.api.domain.model.Workspace;

public interface WorkspaceRepository {
    Workspace save(Workspace workspace);

    Optional<Workspace> findById(Long id);

    List<Workspace> findByOwnerId(Long id);

    Optional<Workspace> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
