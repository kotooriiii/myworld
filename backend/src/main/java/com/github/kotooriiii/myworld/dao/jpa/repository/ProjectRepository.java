package com.github.kotooriiii.myworld.dao.jpa.repository;

import com.github.kotooriiii.myworld.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project>
{

    List<Project> findAllByProjectCollaborators_Author_Id(UUID authorRequesterId);

    Optional<Project> findByIdAndProjectCollaborators_Author_Id(UUID projectId, UUID authorRequesterId);

    Optional<Project> findByTitleAndProjectCollaborators_Author_Id(String title, UUID authorRequesterId);

    boolean existsByIdAndProjectCollaborators_Author_Id(UUID projectId, UUID authorRequesterId);
}