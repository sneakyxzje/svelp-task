package com.API.api.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.api.infrastructure.persistence.entity.BoardEntity;

public interface JpaBoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findByName(String name);

    List<BoardEntity> findAllByWorkspaceId(Long workspaceId);

    Optional<BoardEntity> findBySlugAndWorkspace_Slug(String slug, String workspaceSlug);

    boolean existsBySlugAndWorkspaceId(String slug, Long workspaceId);

}
