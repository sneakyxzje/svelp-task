package com.API.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.api.application.command.CreateWorkspaceCommand;
import com.API.api.application.usecase.workspace.CreateWorkspaceUseCase;
import com.API.api.application.usecase.workspace.GetUserWorkspaceUseCase;
import com.API.api.application.usecase.workspace.GetWorkspaceBySlugUseCase;
import com.API.api.domain.model.Workspace;
import com.API.api.infrastructure.security.UserPrincipal;
import com.API.api.presentation.dto.WorkspaceRequest;
import com.API.api.presentation.dto.WorkspaceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

        private final CreateWorkspaceUseCase createWorkspaceUseCase;
        private final GetUserWorkspaceUseCase getUserWorkspaceUseCase;
        private final GetWorkspaceBySlugUseCase getWorkspaceBySlugUseCase;

        @PostMapping
        public ResponseEntity<WorkspaceResponse> create(
                        @RequestBody WorkspaceRequest request,
                        @AuthenticationPrincipal UserPrincipal principal) {

                CreateWorkspaceCommand command = new CreateWorkspaceCommand(
                                request.name(),
                                principal.getId(),
                                request.description(),
                                request.slug(),
                                request.icon());
                Workspace created = createWorkspaceUseCase.createWorkspace(command);
                return ResponseEntity.status(201).body(new WorkspaceResponse(
                                created.getId(),
                                created.getName(),
                                created.getOwner(),
                                created.getDescription(),
                                created.getSlug(),
                                created.getIcon()));
        }

        @GetMapping
        public ResponseEntity<List<WorkspaceResponse>> getUserWorkspaces(
                        @AuthenticationPrincipal UserPrincipal principal) {
                List<Workspace> workspaces = getUserWorkspaceUseCase.getUserWorkspaces(principal.getId());
                List<WorkspaceResponse> response = workspaces.stream()
                                .map(ws -> new WorkspaceResponse(
                                                ws.getId(),
                                                ws.getName(),
                                                ws.getOwner(),
                                                ws.getDescription(),
                                                ws.getSlug(),
                                                ws.getIcon()))
                                .toList();
                return ResponseEntity.ok(response);
        }

        @GetMapping("/{slug}")
        public ResponseEntity<WorkspaceResponse> getWorkspaceBySlug(
                        @AuthenticationPrincipal UserPrincipal principal,
                        @PathVariable String slug) {
                Workspace workspace = getWorkspaceBySlugUseCase.getWorkspaceBySlug(slug, principal.getId());
                return ResponseEntity.ok(new WorkspaceResponse(
                                workspace.getId(),
                                workspace.getName(),
                                workspace.getOwner(),
                                workspace.getDescription(),
                                workspace.getSlug(),
                                workspace.getIcon()));
        }
}
