package com.github.kotooriiii.myworld.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kotooriiii.myworld.model.id.ProjectCollaboratorId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@IdClass(ProjectCollaboratorId.class)
@Table(name = "project_collaborators")
public class ProjectCollaborator implements GenericModel
{
    public enum AccessLevel
    {
        READ_ONLY,
        EDITOR,
        OWNER
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    @EqualsAndHashCode.Include
    @JsonManagedReference
    private Project project;

    @Id
    @ManyToOne
    @JoinColumn(name = "author_id")
    @EqualsAndHashCode.Include
    @JsonManagedReference
    private Author author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty
    private ProjectCollaborator.AccessLevel accessLevel;
}
