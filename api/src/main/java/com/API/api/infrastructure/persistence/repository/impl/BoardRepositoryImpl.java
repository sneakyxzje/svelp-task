package com.API.api.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.API.api.domain.model.Board;
import com.API.api.domain.repository.BoardRepository;
import com.API.api.infrastructure.persistence.entity.BoardEntity;
import com.API.api.infrastructure.persistence.mapper.BoardMapper;
import com.API.api.infrastructure.persistence.repository.JpaBoardRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private final JpaBoardRepository jpaBoardRepository;

    public Board save(Board board) {
        BoardEntity entity = BoardMapper.toEntity(board);
        BoardEntity save = jpaBoardRepository.save(entity);
        return BoardMapper.toDomain(save);
    }

    public Optional<Board> findByName(String name) {
        Optional<BoardEntity> entity = jpaBoardRepository.findByName(name);
        return entity.map(BoardMapper::toDomain);
    }

    @Override
    public List<Board> findAllByWorkspaceId(Long workspaceId) {
        return jpaBoardRepository.findAllByWorkspaceId(workspaceId).stream()
                .map(BoardMapper::toDomain)
                .collect(Collectors.toList());
    }
}
