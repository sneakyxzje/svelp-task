package com.API.api.application.usecase.workspace;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.API.api.application.command.CreateWorkspaceCommand;
import com.API.api.domain.model.Workspace;
import com.API.api.domain.repository.WorkspaceRepository;

public class CreateWorkspaceUseCase {
    private final WorkspaceRepository workspaceRepository;

    public CreateWorkspaceUseCase(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace createWorkspace(
            CreateWorkspaceCommand command) {
        String finalSlug = (command.slug() == null || command.slug().isBlank())
                ? generateSlug(command.name())
                : generateSlug(command.slug());
        if (workspaceRepository.existsBySlugAndOwnerId(finalSlug, command.owner())) {
            throw new RuntimeException("Slug already exists");
        }
        Workspace workspace = new Workspace(
                null,
                command.name(),
                command.owner(),
                command.description(),
                finalSlug,
                command.icon(),
                null,
                null);
        return workspaceRepository.save(workspace);
    }

    public String generateSlug(String input) {
        if (input == null)
            return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalized).replaceAll("");
        return slug.toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
