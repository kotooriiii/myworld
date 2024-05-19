package com.github.kotooriiii.myworld.dao.jpa;

import com.github.kotooriiii.myworld.dao.jpa.repository.AuthorRepository;
import com.github.kotooriiii.myworld.dao.jpa.repository.ProjectCollaboratorRepository;
import com.github.kotooriiii.myworld.dao.jpa.repository.ProjectRepository;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.dao.ProjectDao;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("jpa")
public class ProjectJPADaoImpl implements ProjectDao {

    private ProjectRepository projectRepository;
    private ProjectCollaboratorRepository projectCollaboratorRepository;
    private AuthorRepository authorRepository;
    private ProjectValidator projectValidator;

    @Override
    public void createProject(Project projectToCreate) {
        projectRepository.save(projectToCreate);
    }

    @Override
    public ProjectCollaborator addCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId, ProjectCollaborator.AccessLevel accessLevel) {
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProject(projectRepository.findById(projectId).orElseThrow()); //todo?
        projectCollaborator.setAuthor(authorRepository.findById(collaboratorId).orElseThrow());  //todo?
        projectCollaborator.setAccessLevel(accessLevel);
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    @Override
    public List<Project> selectAllProjects(UUID authorRequesterId) {
        return projectRepository.findAllByProjectCollaborators_Author_Id(authorRequesterId);
    }

    @Override
    public Page<Project> selectProjectsByPage(Pageable pageable, QueryExpression filterQueryExpression, UUID authorRequesterId) {
        JPAResult<Project> jpaResult = projectValidator.withQueryExpression(filterQueryExpression).buildJPA();

        Specification<Project> specification = jpaResult.specification();

        return projectRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Project> selectProjectById(UUID projectId, UUID authorRequesterId) {
        return projectRepository.findByIdAndProjectCollaborators_Author_Id(projectId, authorRequesterId);
    }

    @Override
    public Optional<Project> selectProjectByTitle(String title, UUID authorRequesterId) {
        return projectRepository.findByTitleAndProjectCollaborators_Author_Id(title, authorRequesterId);
    }

    @Override
    public List<ProjectCollaborator> selectCollaboratorsByProjectId(UUID projectId) {
        return projectCollaboratorRepository.findAllByProject_Id(projectId);
    }

    @Override
    public Optional<ProjectCollaborator> selectCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId) {
        return projectCollaboratorRepository.findByProject_IdAndAuthor_Id(projectId, collaboratorId);
    }

    @Override
    public boolean existsProjectById(UUID projectId, UUID authorRequesterId) {
        return projectRepository.existsByIdAndProjectCollaborators_Author_Id(projectId, authorRequesterId);
    }

    @Override
    public void updateProject(Project projectToUpdate) {
        projectRepository.save(projectToUpdate);
    }

    @Override
    public void updateCollaboratorByProjectIdAndByAuthorId(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.save(projectCollaborator);

    }

    @Override
    public void deleteProjectById(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public void removeCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId) {
        projectCollaboratorRepository.deleteByProject_IdAndAuthor_Id(projectId, collaboratorId);

    }
}