package com.github.kotooriiii.myworld.dao.jpa.repository;

import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, UUID>
{

    List<ProjectCollaborator> findAllByProject_Id(UUID projectId);

    Optional<ProjectCollaborator> findByProject_IdAndAuthor_Id(UUID projectId, UUID authorId);

    void deleteByProject_IdAndAuthor_Id(UUID projectId, UUID authorId);

}