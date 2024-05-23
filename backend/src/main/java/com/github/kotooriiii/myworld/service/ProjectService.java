package com.github.kotooriiii.myworld.service;


import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.dao.ProjectDao;
import com.github.kotooriiii.myworld.dto.ProjectCollaboratorDTO;
import com.github.kotooriiii.myworld.dto.ProjectDTO;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCollaboratorAddRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCollaboratorUpdateRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCreateRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectUpdateRequest;
import com.github.kotooriiii.myworld.exception.RequestValidationException;
import com.github.kotooriiii.myworld.exception.ResourceExistsException;
import com.github.kotooriiii.myworld.exception.ResourceNotFoundException;
import com.github.kotooriiii.myworld.exception.ForbiddenException;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.service.mapper.ProjectCollaboratorMapper;
import com.github.kotooriiii.myworld.service.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectService
{

    @Qualifier("jdbc")
    private final ProjectDao projectDao;

    private final ProjectMapper projectMapper;
    private final ProjectCollaboratorMapper projectCollaboratorMapper;

    private final AuthorService authorService;


    public ProjectDTO createProject(ProjectCreateRequest projectToCreate)
    {

        UUID authorRequesterId = authorService.getCurrentUserId();

        LocalDateTime now = LocalDateTime.now();


        Project project = Project.builder()
                .id(UUID.randomUUID())
                .title(projectToCreate.title())
                .description(projectToCreate.description())
                .createdAt(now)
                .createdBy(authorRequesterId)
                .lastUpdatedAt(now)
                .lastUpdatedBy(authorRequesterId)
                .build();
        projectDao.createProject(project);
        projectDao.addCollaboratorByProjectIdAndByAuthorId(project.getId(), authorRequesterId, ProjectCollaborator.AccessLevel.OWNER);

        return projectMapper.apply(projectDao.selectProjectById(project.getId(), authorRequesterId).orElseThrow());
    }

    public ProjectCollaboratorDTO addCollaborator(UUID projectId, ProjectCollaboratorAddRequest projectCollaboratorAddRequest)
    {
        UUID authorRequesterId = authorService.getCurrentUserId();

        ProjectCollaborator projectTargetCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, projectCollaboratorAddRequest.collaboratorId()); //todo chang eorder? why is someone allowed to see THAT SOMEOENE IS IN A GROYUP
        if(projectTargetCollaborator != null)
        {
            throw new ResourceExistsException("The collaborator has already been added to this project.", projectTargetCollaborator);
        }


        ProjectCollaborator projectSelfCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, authorRequesterId);

        if(!isProjectAdmin(projectSelfCollaborator))
        {
            throw new ForbiddenException("You are not allowed to add collaborators to this project"); //todo maybe use bundle messages for language support, for this exception, and all the rest in this CLASS
        }

        if(projectCollaboratorAddRequest.accessLevel() == null)
        {
            return projectCollaboratorMapper.apply(projectDao.addCollaboratorByProjectIdAndByAuthorId(projectId, projectCollaboratorAddRequest.collaboratorId()));

        }
        return projectCollaboratorMapper.apply(projectDao.addCollaboratorByProjectIdAndByAuthorId(projectId, projectCollaboratorAddRequest.collaboratorId(), projectCollaboratorAddRequest.accessLevel()));
    }

    public List<ProjectDTO> getAllProjects()
    {
        UUID authorRequesterId = authorService.getCurrentUserId();

        return projectDao
                .selectAllProjects(authorRequesterId)
                .stream()
                .map(projectMapper)
                .collect(Collectors.toList());
    }



    public Page<ProjectDTO> getProjectsByPage(Pageable pageable, QueryExpression filterQueryExpression)
    {
        UUID authorRequesterId = authorService.getCurrentUserId();
        return projectDao
                .selectProjectsByPage(pageable, filterQueryExpression, authorRequesterId)
                .map(projectMapper);
    }

    public ProjectDTO getProjectById(UUID projectId)
    {
        Project project = checkIfProjectExistsOrThrow(projectId);
        return projectMapper.apply(project);
    }

    public List<ProjectCollaboratorDTO> getProjectCollaborators(UUID projectId)
    {

        checkIfProjectExistsOrThrow(projectId);

        return projectDao
                .selectCollaboratorsByProjectId(projectId)
                .stream()
                .map(projectCollaboratorMapper)
                .collect(Collectors.toList());
    }

    public ProjectCollaboratorDTO getProjectCollaborator(UUID projectId, UUID authorTargetCollaboratorId)
    {

        ProjectCollaborator projectCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, authorTargetCollaboratorId);
        return projectCollaboratorMapper.apply(projectCollaborator);
    }

    public void updateProject(UUID projectId, ProjectUpdateRequest projectUpdateRequest)
    {

        // TODO: for JPA use .getReferenceById(authorId) as it does does not bring object into memory and instead a reference
        Project project = checkIfProjectExistsOrThrow(projectId);

        boolean changes = false;

        if (projectUpdateRequest.title() != null && !projectUpdateRequest.title().equals(project.getTitle()))
        {
            project.setTitle(projectUpdateRequest.title());
            changes = true;
        }

        if (projectUpdateRequest.description() != null && !projectUpdateRequest.description().equals(project.getDescription()))
        {
            project.setDescription(projectUpdateRequest.description());
            changes = true;
        }

        if (projectUpdateRequest.imageIconId() != null && !projectUpdateRequest.imageIconId().equals(project.getImageIconId()))
        {
            project.setImageIconId(projectUpdateRequest.imageIconId());
            changes = true;
        }


        if (!changes)
        {
            throw new RequestValidationException("no data changes found");
        }

        projectDao.updateProject(project);
    }

    public void updateProjectCollaborator(UUID projectId, ProjectCollaboratorUpdateRequest projectCollaboratorUpdateRequest)
    {

        // TODO: for JPA use .getReferenceById(authorId) as it does does not bring object into memory and instead a reference
        ProjectCollaborator projectCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, projectCollaboratorUpdateRequest.collaboratorId());

        boolean changes = false;

        if (projectCollaboratorUpdateRequest.accessLevel() != null && !projectCollaboratorUpdateRequest.accessLevel().equals(projectCollaborator.getAccessLevel()))
        {
            projectCollaborator.setAccessLevel(projectCollaboratorUpdateRequest.accessLevel());
            changes = true;
        }

        if (!changes)
        {
            throw new RequestValidationException("no data changes found");
        }

        projectDao.updateCollaboratorByProjectIdAndByAuthorId(projectCollaborator);
    }


    public void deleteProjectById(UUID projectId)
    {
        UUID authorRequesterId = authorService.getCurrentUserId();
        ProjectCollaborator projectCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, authorRequesterId);
        if(!isProjectOwner(projectCollaborator))
        {
            throw new ForbiddenException("You are not allowed to delete this project");
        }

        projectDao.deleteProjectById(projectId);
    }

    public void removeCollaborator(UUID projectId, UUID collaboratorId)
    {
        UUID authorRequesterId = authorService.getCurrentUserId();

        //check if collaborator exists on the project
        checkIfProjectCollaboratorExistsOrThrow(projectId, collaboratorId);

        ProjectCollaborator projectSelfCollaborator = checkIfProjectCollaboratorExistsOrThrow(projectId, authorRequesterId);

        if(!isProjectAdmin(projectSelfCollaborator))
        {
            throw new ForbiddenException("You are not allowed to remove collaborators to this project");
        }

        projectDao.removeCollaboratorByProjectIdAndByAuthorId(projectId, collaboratorId);
    }



    //
    //
    // HELPER METHODS
    //
    //

    private Project checkIfProjectExistsOrThrow(UUID projectId)
    {
        UUID authorRequesterId = authorService.getCurrentUserId();


        return projectDao.selectProjectById(projectId, authorRequesterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with ID [%s] not found or no permission.".formatted(projectId)
                ));
    }


    private ProjectCollaborator checkIfProjectCollaboratorExistsOrThrow(UUID projectId, UUID authorCollaboratorId)
    {
        checkIfProjectExistsOrThrow(projectId);
        return projectDao.selectCollaboratorByProjectIdAndByAuthorId(projectId, authorCollaboratorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project collaborator with ID [%s] not found.".formatted(authorCollaboratorId)
                ));
    }

    private boolean isProjectAdmin(ProjectCollaborator projectCollaborator)
    {
        return ProjectCollaborator.AccessLevel.EDITOR.equals(projectCollaborator.getAccessLevel()) || ProjectCollaborator.AccessLevel.OWNER.equals(projectCollaborator.getAccessLevel());
    }

    private boolean isProjectOwner(ProjectCollaborator projectCollaborator)
    {
        return ProjectCollaborator.AccessLevel.OWNER.equals(projectCollaborator.getAccessLevel());
    }

}

