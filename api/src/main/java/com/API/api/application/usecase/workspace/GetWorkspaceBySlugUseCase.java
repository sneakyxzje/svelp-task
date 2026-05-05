package com.API.api.application.usecase.workspace;

import com.API.api.application.service.WorkspacePermissionService;
import com.API.api.domain.model.Workspace;
import com.API.api.domain.repository.WorkspaceRepository;

public class GetWorkspaceBySlugUseCase {

    private final WorkspaceRepository workspaceRepository;

    private final WorkspacePermissionService workspacePermissionService;

    public GetWorkspaceBySlugUseCase(WorkspaceRepository workspaceRepository,
            WorkspacePermissionService workspacePermissionService) {
        this.workspaceRepository = workspaceRepository;
        this.workspacePermissionService = workspacePermissionService;
    }

    public Workspace getWorkspaceBySlug(String slug, Long userId) {
        Workspace workspace = workspaceRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        workspacePermissionService.verifyOwner(workspace, userId);

        return workspace;
    }
}
