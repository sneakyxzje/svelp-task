package com.API.api.application.service;

import com.API.api.domain.model.Column;
import com.API.api.domain.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnPermissionService {
    private final ColumnRepository columnRepository;
    private final BoardPermissionService boardPermissionService;

    public Column hasPermission(Long userId, Long columnId) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        
        // Bắc cầu lên Board để check quyền
        boardPermissionService.hasPermission(userId, column.getBoardId());
        
        return column;
    }
}
