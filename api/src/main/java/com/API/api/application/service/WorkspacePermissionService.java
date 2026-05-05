package com.API.api.application.service;

import com.API.api.domain.model.Workspace;
import com.API.api.domain.repository.WorkspaceRepository;

public class WorkspacePermissionService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspacePermissionService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public void hasPermission(Long userId, Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
        verifyOwner(workspace, userId);
    }

    public void hasPermission(Long userId, String slug) {
        Workspace workspace = workspaceRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
        verifyOwner(workspace, userId);
    }

    public void verifyOwner(Workspace workspace, Long userId) {
        if (!workspace.getOwner().equals(userId)) {
            throw new RuntimeException("You don't have permission to access this workspace");
        }
    }
}
