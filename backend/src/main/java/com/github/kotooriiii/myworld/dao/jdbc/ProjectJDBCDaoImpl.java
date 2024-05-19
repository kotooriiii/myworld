package com.github.kotooriiii.myworld.dao.jdbc;

import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.dao.ProjectDao;
import com.github.kotooriiii.myworld.dao.jdbc.mapper.ProjectCollaboratorRowMapper;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbc")
public class ProjectJDBCDaoImpl implements ProjectDao
{
    private static final String SQL_CREATE_PROJECT = """
            INSERT INTO projects(id, title, description, image_icon_id, created_at, last_updated_at, created_by, last_updated_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String SQL_ADD_COLLABORATOR = """
            INSERT INTO project_collaborators(project_id, author_id, access_level)
            VALUES (?, ?, ?);
            """;

    private static final String SQL_SELECT_ALL_PROJECTS = """
            SELECT projects.*
            FROM projects
            INNER JOIN project_collaborators pc ON pc.project_id = id
            WHERE pc.author_id = :author_requester_id
             """;

    private static final String SQL_SELECT_PROJECTS_BY_PAGE = """
            SELECT projects.*
            FROM projects
            {innerJoin}
            WHERE pc.author_id = :author_requester_id AND {whereClause}
            LIMIT :limit
            OFFSET :offset
            """;

    private static final String SQL_PROJECT_COUNT = """
            SELECT COUNT(*)
            FROM projects
            INNER JOIN project_collaborators pc ON pc.project_id = id
            WHERE pc.author_id = :author_requester_id
            """;
    private static final String SQL_SELECT_PROJECT_BY_ID = """
            SELECT projects.*
            FROM projects
            INNER JOIN project_collaborators pc ON pc.project_id = id
            WHERE pc.author_id = :author_requester_id AND id = :id
            """;

    private static final String SQL_SELECT_PROJECT_BY_TITLE = """
            SELECT projects.*
            FROM projects
            INNER JOIN project_collaborators pc ON pc.project_id = id
            WHERE pc.author_id = :author_requester_id AND pc.access_level = :access_level AND title = :title
            """;
    private static final String SQL_SELECT_COLLABORATORS_BY_PROJECT_ID = """
            SELECT
                p.id AS p_id,
                p.title AS p_title,
                p.description AS p_description,
                p.image_icon_id AS p_image_icon_id,
                
                a.id AS a_id,
                a.name AS a_name,
                a.email AS a_email,
                a.birth_date AS a_birth_date,
                a.gender AS a_gender,
                a.image_icon_id AS a_image_icon_id,
               
                pc.access_level AS pc_access_level
            FROM
                project_collaborators pc
            INNER JOIN
                projects p ON pc.project_id = p.id
            INNER JOIN
                authors a ON pc.author_id = a.id
            WHERE project_id = :project_id;
            """;
    private static final String SQL_SELECT_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID = """
            SELECT
                p.id AS p_id,
                p.title AS p_title,
                p.description AS p_description,
                p.image_icon_id AS p_image_icon_id,
                
                a.id AS a_id,
                a.name AS a_name,
                a.email AS a_email,
                a.birth_date AS a_birth_date,
                a.gender AS a_gender,
                a.image_icon_id AS a_image_icon_id,
               
                pc.access_level AS pc_access_level
            FROM
                project_collaborators pc
            INNER JOIN
                projects p ON pc.project_id = p.id
            INNER JOIN
                authors a ON pc.author_id = a.id
            WHERE
                pc.project_id = :project_id
                AND pc.author_id = :author_id;
            """;

    private static final String SQL_EXISTS_PROJECT_BY_ID = """
            SELECT count(id)
            FROM projects
            INNER JOIN project_collaborators pc ON pc.project_id = id
            WHERE pc.author_id = :author_requester_id AND id = :id
            """;
    private static final String SQL_UPDATE_PROJECT = """
            UPDATE projects
            SET title = :title, description = :description, image_icon_id = :image_icon_id
            WHERE id = :id
            """;
    private static final String SQL_UPDATE_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID = """
             UPDATE project_collaborators
             SET access_level = :access_level
             WHERE project_id = :project_id AND author_id = :author_id;
            """;
    private static final String SQL_DELETE_PROJECT_BY_ID = """
            DELETE
            FROM projects
            WHERE id = :id
            """;
    private static final String SQL_REMOVE_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID = """
            DELETE
            FROM project_collaborators
            WHERE project_id = :project_id AND author_id = :author_id
            """;


    private final JdbcClient jdbcClient;
    private final ProjectValidator projectValidator;

    @Override
    public void createProject(Project projectToCreate)
    {
        //todo do id in service
        int result = jdbcClient.sql(SQL_CREATE_PROJECT)
                .params(Stream.of(projectToCreate.getId(), projectToCreate.getTitle(), projectToCreate.getDescription(), projectToCreate.getImageIconId(), projectToCreate.getCreatedAt(), projectToCreate.getLastUpdatedAt(), projectToCreate.getCreatedBy(), projectToCreate.getLastUpdatedBy()).toList())
                .update();
        Assert.state(result == 1, "Failed to create project \"" + projectToCreate + "\".");
    }

    @Override
    public ProjectCollaborator addCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId,
                                                                       ProjectCollaborator.AccessLevel accessLevel)
    {
        //todo do id in service
        int result = jdbcClient.sql(SQL_ADD_COLLABORATOR)
                .params(List.of(projectId, collaboratorId, accessLevel.name()))
                .update();
        Assert.state(result == 1, "Failed to add collaborator \"" + collaboratorId + "\" to project \"" + projectId + "\".");
        return selectCollaboratorByProjectIdAndByAuthorId(projectId, collaboratorId).orElseThrow(); //todo throw what ?
    }

    @Override
    public List<Project> selectAllProjects(UUID authorRequesterId)
    {
        return jdbcClient.sql(SQL_SELECT_ALL_PROJECTS)
                .param("author_requester_id", authorRequesterId)
                .query(Project.class)
                .list();
    }

    @Override
    public Page<Project> selectProjectsByPage(Pageable pageable, QueryExpression filterQueryExpression,
                                              UUID authorRequesterId)
    {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        //Requirements
        Map<String, Object> map = new HashMap<>();
        map.put("author_requester_id", authorRequesterId);
        map.put("limit", pageSize);
        map.put("offset", offset);

        JDBCResult jdbcResult = projectValidator.withQueryExpression(filterQueryExpression).buildJDBC();

        map.putAll(jdbcResult.valueMap());


        Map<String, String> values = new HashMap<>();
        values.put("innerJoin", jdbcResult.innerJoin());
        values.put("whereClause", jdbcResult.whereClause());

        StringSubstitutor sub = new StringSubstitutor(values, "{", "}");
        String SQL_SELECT_PROJECTS_BY_PAGE_FORMATTED = sub.replace(SQL_SELECT_PROJECTS_BY_PAGE);


        List<Project> projects = jdbcClient.sql(SQL_SELECT_PROJECTS_BY_PAGE_FORMATTED)
                .params(map)
                .query(Project.class)
                .list();

        //todo check exceptions and make custom one for internal error. this is dev issue!!! atm, it shows SQL error which is likely not a good idea lol

        int totalCount = jdbcClient.sql(SQL_PROJECT_COUNT)
                .param("author_requester_id", authorRequesterId)
                .query(Integer.class)
                .single();

        return new PageImpl<Project>(projects, pageable, totalCount);
    }

    @Override
    public Optional<Project> selectProjectById(UUID projectId, UUID authorRequesterId)
    {
        return jdbcClient.sql(SQL_SELECT_PROJECT_BY_ID)
                .param("author_requester_id", authorRequesterId)
                .param("id", projectId)
                .query(Project.class)
                .optional();
    }

    @Override
    public Optional<Project> selectProjectByTitle(String title, UUID authorRequesterId)
    {
        return jdbcClient.sql(SQL_SELECT_PROJECT_BY_TITLE)
                .param("author_requester_id", authorRequesterId)
                .param("access_level", ProjectCollaborator.AccessLevel.OWNER)
                .param("title", title)
                .query(Project.class)
                .optional();
    }

    @Override
    public List<ProjectCollaborator> selectCollaboratorsByProjectId(UUID projectId)
    {
        return jdbcClient.sql(SQL_SELECT_COLLABORATORS_BY_PROJECT_ID)
                .param("project_id", projectId)
                .query(new ProjectCollaboratorRowMapper())
                .list();
    }

    @Override
    public Optional<ProjectCollaborator> selectCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId)
    {
        return jdbcClient.sql(SQL_SELECT_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID)
                .param("project_id", projectId)
                .param("author_id", collaboratorId)
                .query(new ProjectCollaboratorRowMapper())
                .optional();
    }

    @Override
    public boolean existsProjectById(UUID projectId, UUID authorRequesterId)
    {
        int count = jdbcClient.sql(SQL_EXISTS_PROJECT_BY_ID)
                .param("id", projectId)
                .param("author_requester_id", authorRequesterId)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    @Override
    public void updateProject(Project projectToUpdate)
    {
        var updated = jdbcClient.sql(SQL_UPDATE_PROJECT)
                .param("title", projectToUpdate.getTitle())
                .param("description", projectToUpdate.getDescription())
                .param("image_icon_id", projectToUpdate.getImageIconId())
                .param("id", projectToUpdate.getId())
                .update();

        Assert.state(updated == 1, "Failed to update project " + projectToUpdate);

    }

    @Override
    public void updateCollaboratorByProjectIdAndByAuthorId(ProjectCollaborator projectCollaborator)
    {
        var updated = jdbcClient.sql(SQL_UPDATE_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID)
                .param("access_level", projectCollaborator.getAccessLevel())
                .param("project_id", projectCollaborator.getProject().getId())
                .param("author_id", projectCollaborator.getAuthor().getId())
                .update();

        Assert.state(updated == 1, "Failed to update collaborator " + projectCollaborator + ".");
    }

    @Override
    public void deleteProjectById(UUID projectId)
    {
        int result = jdbcClient.sql(SQL_DELETE_PROJECT_BY_ID)
                .param("id", projectId)
                .update();

        Assert.state(result == 1, "Failed to delete project with ID : " + projectId);
    }

    @Override
    public void removeCollaboratorByProjectIdAndByAuthorId(UUID projectId, UUID collaboratorId)
    {
        int result = jdbcClient.sql(SQL_REMOVE_COLLABORATOR_BY_PROJECT_ID_AND_BY_AUTHOR_ID)
                .param("project_id", projectId)
                .param("author_id", collaboratorId)
                .update();

        Assert.state(result == 1, "Failed to delete project with ID : " + projectId);
    }
}
