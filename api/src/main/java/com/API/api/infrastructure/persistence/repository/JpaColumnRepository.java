package com.API.api.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.API.api.infrastructure.persistence.entity.ColumnEntity;

public interface JpaColumnRepository extends JpaRepository<ColumnEntity, Long> {
    Optional<ColumnEntity> findById(Long id);

    @Query("SELECT MAX(c.position) FROM ColumnEntity c WHERE c.board.id = :boardId")
    Double findMaxPositionByBoardId(Long boardId);

    List<ColumnEntity> findAllByBoardIdOrderByPositionAsc(Long boardId);
}
