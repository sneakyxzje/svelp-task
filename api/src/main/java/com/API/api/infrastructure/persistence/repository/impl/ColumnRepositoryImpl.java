package com.API.api.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.API.api.domain.model.Column;
import com.API.api.domain.repository.ColumnRepository;
import com.API.api.infrastructure.persistence.entity.ColumnEntity;
import com.API.api.infrastructure.persistence.mapper.ColumnMapper;
import com.API.api.infrastructure.persistence.repository.JpaColumnRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ColumnRepositoryImpl implements ColumnRepository {
    private final JpaColumnRepository jpaColumnRepository;

    public Column save(Column column) {
        ColumnEntity entity = ColumnMapper.toEntity(column);
        ColumnEntity saved = jpaColumnRepository.save(entity);
        return ColumnMapper.toDomain(saved);
    }

    public Optional<Column> findById(Long id) {
        return jpaColumnRepository.findById(id).map(ColumnMapper::toDomain);
    }

    public Double findMaxPositionByBoardId(Long boardId) {
        return jpaColumnRepository.findMaxPositionByBoardId(boardId);
    }

    public List<Column> findAllByBoardId(Long boardId) {
        return jpaColumnRepository.findAllByBoardIdOrderByPositionAsc(boardId).stream()
                .map(ColumnMapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
