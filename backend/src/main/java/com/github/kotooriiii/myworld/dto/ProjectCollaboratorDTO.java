package com.github.kotooriiii.myworld.dto;

import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;

import java.util.UUID;

public record ProjectCollaboratorDTO(
        ProjectDTO projectDTO,
        AuthorDTO authorDTO,
        ProjectCollaborator.AccessLevel accessLevel
){

}
