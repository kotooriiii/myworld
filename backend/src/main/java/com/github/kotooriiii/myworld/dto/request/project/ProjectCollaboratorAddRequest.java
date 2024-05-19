package com.github.kotooriiii.myworld.dto.request.project;

import com.github.kotooriiii.myworld.model.ProjectCollaborator;

import java.util.UUID;

public record ProjectCollaboratorAddRequest(
        UUID collaboratorId,
        ProjectCollaborator.AccessLevel accessLevel
) {
}
