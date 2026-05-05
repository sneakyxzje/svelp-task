package com.API.api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.API.api.domain.model.Board;

public interface BoardRepository {
    Board save(Board board);

    Optional<Board> findByName(String name);

    List<Board> findAllByWorkspaceId(Long workspaceId);
}
