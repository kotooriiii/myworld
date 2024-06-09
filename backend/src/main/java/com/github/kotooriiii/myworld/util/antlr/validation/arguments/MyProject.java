package com.github.kotooriiii.myworld.util.antlr.validation.arguments;

import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectQEDefinition;

import java.util.UUID;

public class MyProject extends ArgumentStep<Project, ProjectQEDefinition>
{
    public MyProject()
    {
        super(ProjectQEDefinition.getInstance());
    }


    public MyProject withAuthorRequesterId(UUID author_requester_id)
    {
        putArgs(ProjectQEDefinition.getInstance().getAuthorRequesterIdKey(), author_requester_id);
        return this;
    }
}
