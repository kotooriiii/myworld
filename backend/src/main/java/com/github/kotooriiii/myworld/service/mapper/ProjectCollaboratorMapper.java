package com.github.kotooriiii.myworld.service.mapper;

import com.github.kotooriiii.myworld.dto.ProjectCollaboratorDTO;
import com.github.kotooriiii.myworld.dto.ProjectDTO;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProjectCollaboratorMapper implements Function<ProjectCollaborator, ProjectCollaboratorDTO> {

    private final AuthorMapper authorMapper;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectCollaboratorDTO apply(ProjectCollaborator projectCollaborator) {
        return new ProjectCollaboratorDTO(
                projectMapper.apply(projectCollaborator.getProject()),
                authorMapper.apply(projectCollaborator.getAuthor()),
                projectCollaborator.getAccessLevel()
        );
    }
}
