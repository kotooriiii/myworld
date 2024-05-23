package com.github.kotooriiii.myworld.service.mapper;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.ProjectDTO;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.Project;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectMapper implements Function<Project, ProjectDTO> {
    @Override
    public ProjectDTO apply(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription()
        );
    }
}
