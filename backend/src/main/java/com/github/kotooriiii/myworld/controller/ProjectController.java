package com.github.kotooriiii.myworld.controller;

import com.github.kotooriiii.myworld.dto.ProjectCollaboratorDTO;
import com.github.kotooriiii.myworld.dto.ProjectDTO;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCollaboratorAddRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCollaboratorUpdateRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectCreateRequest;
import com.github.kotooriiii.myworld.dto.request.project.ProjectUpdateRequest;
import com.github.kotooriiii.myworld.service.ProjectService;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
@Tag(name = "Project Controller", description = "Project management API")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO createProject(@RequestBody ProjectCreateRequest projectCreateRequest) {
        return projectService.createProject(projectCreateRequest);
    }

    @Operation(summary = "Get project by ID")
    @GetMapping("/{projectId}")
    public ProjectDTO getProjectById(@PathVariable UUID projectId) {
        return projectService.getProjectById(projectId);
    }

    @Operation(summary = "Get project collaborators")
    @GetMapping("/{projectId}/collaborators")
    public List<ProjectCollaboratorDTO> getProjectCollaborators(@PathVariable UUID projectId) {
        return projectService.getProjectCollaborators(projectId);
    }

    @Operation(summary = "Get project collaborator by ID")
    @GetMapping("/{projectId}/collaborators/{authorTargetCollaboratorId}")
    public ProjectCollaboratorDTO getProjectCollaborator(@PathVariable UUID projectId,
                                                         @PathVariable UUID authorTargetCollaboratorId) {
        return projectService.getProjectCollaborator(projectId, authorTargetCollaboratorId);
    }

    @Operation(summary = "Update project")
    @PutMapping("/{projectId}")
    public void updateProject(@PathVariable UUID projectId, @RequestBody ProjectUpdateRequest projectUpdateRequest) {
        projectService.updateProject(projectId, projectUpdateRequest);
    }

    @Operation(summary = "Update project collaborator")
    @PutMapping("/{projectId}/collaborators/{authorTargetCollaboratorId}")
    public void updateProjectCollaborator(@PathVariable UUID projectId, @PathVariable UUID authorTargetCollaboratorId,
                                          @RequestBody ProjectCollaboratorUpdateRequest projectCollaboratorUpdateRequest) {
        projectService.updateProjectCollaborator(projectId, projectCollaboratorUpdateRequest);
    }

    @Operation(summary = "Delete project by ID")
    @DeleteMapping("/{projectId}")
    public void deleteProjectById(@PathVariable UUID projectId) {
        projectService.deleteProjectById(projectId);
    }

    @Operation(summary = "Remove collaborator from project")
    @DeleteMapping("/{projectId}/collaborators/{collaboratorId}")
    public void removeCollaborator(@PathVariable UUID projectId, @PathVariable UUID collaboratorId) {
        projectService.removeCollaborator(projectId, collaboratorId);
    }

    @Operation(summary = "Add collaborator to project")
    @PostMapping("/{projectId}/collaborators")
    public ProjectCollaboratorDTO addCollaborator(@PathVariable UUID projectId,
                                                  @RequestBody ProjectCollaboratorAddRequest projectCollaboratorAddRequest) {
        return projectService.addCollaborator(projectId, projectCollaboratorAddRequest);
    }

    @Operation(summary = "Get projects by page")
    @GetMapping
    public Page<ProjectDTO> getProjectsByPage(@PageableDefault(size = 20, page = 0) Pageable pageable,
                                              @RequestParam(value = "filter", required=false, defaultValue = "") String filter) {
        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression(filter);

        return projectService.getProjectsByPage(pageable, filterQueryExpression);
    }
}