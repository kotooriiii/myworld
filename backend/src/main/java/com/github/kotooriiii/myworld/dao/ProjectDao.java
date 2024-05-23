package com.github.kotooriiii.myworld.dao;

import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDao
{
    void createProject(Project projectToCreate);

    default ProjectCollaborator addCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId)
    {
        return addCollaboratorByProjectIdAndByAuthorId(projectId, collaboratorId, ProjectCollaborator.AccessLevel.READ_ONLY);
    }

    ProjectCollaborator addCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId, ProjectCollaborator.AccessLevel accessLevel);

    List<Project> selectAllProjects(UUID authorRequesterId);

    Page<Project> selectProjectsByPage(Pageable pageable, QueryExpression filterQueryExpression, UUID authorRequesterId);

    Optional<Project> selectProjectById(UUID projectId, UUID authorRequesterId);
    Optional<Project> selectProjectByTitle(String title, UUID authorRequesterId);

    List<ProjectCollaborator> selectCollaboratorsByProjectId(UUID projectId);
    Optional<ProjectCollaborator> selectCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId);
    boolean existsProjectById(UUID projectId, UUID authorRequesterId);

    void updateProject(Project projectToUpdate);
    void updateCollaboratorByProjectIdAndByAuthorId(ProjectCollaborator projectCollaborator);
    void deleteProjectById(UUID projectId);

    void removeCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId);
}
