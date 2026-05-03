package com.API.api.application.usecase.workspace;

import java.util.List;

import com.API.api.domain.model.Workspace;
import com.API.api.domain.repository.WorkspaceRepository;

public class GetUserWorkspaceUseCase {
    private final WorkspaceRepository workspaceRepository;

    public GetUserWorkspaceUseCase(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<Workspace> getUserWorkspaces(Long userId) {
        return workspaceRepository.findByOwnerId(userId);
    }
}
