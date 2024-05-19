package com.github.kotooriiii.myworld.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "projects")
public class Project implements GenericModel {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    @JsonProperty
    private String title;

    @Column(nullable = false)
    @JsonProperty
    private String description;

    @Column
    @JsonProperty
    private String imageIconId;

    @Column
    @JsonProperty
    private LocalDateTime createdAt;

    @Column
    @JsonProperty
    private LocalDateTime lastUpdatedAt;

    @Column
    @JsonProperty
    private UUID createdBy;

    @Column
    @JsonProperty
    private UUID lastUpdatedBy;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    @JsonBackReference
    private Set<ProjectCollaborator> projectCollaborators;
}