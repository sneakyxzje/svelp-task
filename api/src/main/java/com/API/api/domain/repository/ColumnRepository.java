package com.API.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.API.api.domain.model.Column;

public interface ColumnRepository {

    Column save(Column column);

    Optional<Column> findById(Long id);

    Double findMaxPositionByBoardId(Long boardId);

    List<Column> findAllByBoardId(Long boardId);

}
