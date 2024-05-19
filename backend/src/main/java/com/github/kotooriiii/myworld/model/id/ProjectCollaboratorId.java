package com.github.kotooriiii.myworld.model.id;

import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.Project;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

public class ProjectCollaboratorId implements Serializable
{
    private Project project;
    private Author author;
}
