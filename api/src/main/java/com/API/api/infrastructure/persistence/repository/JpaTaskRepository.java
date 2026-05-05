package com.API.api.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.API.api.infrastructure.persistence.entity.TaskEntity;
import java.util.List;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByColumnIdOrderByPositionAsc(Long columnId);

    @Query("SELECT MAX(t.position) FROM TaskEntity t WHERE t.column.id = :columnId")
    Double findMaxPositionByColumnId(@Param("columnId") Long columnId);

    List<TaskEntity> findAllByColumnIdInOrderByPositionAsc(List<Long> columnIds);
}
