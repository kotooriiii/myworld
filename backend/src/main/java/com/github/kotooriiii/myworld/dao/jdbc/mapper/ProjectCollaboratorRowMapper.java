package com.github.kotooriiii.myworld.dao.jdbc.mapper;

import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProjectCollaboratorRowMapper implements RowMapper<ProjectCollaborator> {

    @Override
    public ProjectCollaborator mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();

        // Mapping projects fields

        Project project = new Project();
        project.setId((UUID) rs.getObject("p_id"));
        project.setTitle(rs.getString("p_title"));
        project.setDescription(rs.getString("p_description"));
        project.setImageIconId(rs.getString("p_image_icon_id"));

        // Mapping authors fields
        Author author = new Author();
        author.setId((UUID) rs.getObject("a_id")); // Assuming author_id is the primary key of authors
        author.setName(rs.getString("a_name"));
        author.setEmail(rs.getString("a_email"));
        author.setBirthDate(rs.getTimestamp("a_birth_date").toLocalDateTime().toLocalDate());
        author.setGender(Gender.valueOf(rs.getString("a_gender")));
        author.setImageIconId(rs.getString("a_image_icon_id"));

        // Mapping project_collaborators fields
        projectCollaborator.setAccessLevel(ProjectCollaborator.AccessLevel.valueOf(rs.getString("pc_access_level")));
        projectCollaborator.setProject(project);
        projectCollaborator.setAuthor(author);

        return projectCollaborator;
    }
}
