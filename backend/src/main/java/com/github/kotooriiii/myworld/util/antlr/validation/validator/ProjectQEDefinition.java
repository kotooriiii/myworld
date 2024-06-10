package com.github.kotooriiii.myworld.util.antlr.validation.validator;

import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl.custom.ProjectCollaboratorJDBCStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.custom.ProjectCollaboratorJPAStrategy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProjectQEDefinition extends BaseQEDefinition<Project>
{
    private static ProjectQEDefinition INSTANCE = null;

    public final static String AUTHOR_REQUESTER_ID = "author_requester_id";
    private ProjectQEDefinition() {
        super(Project.class);
    }
    public static synchronized ProjectQEDefinition getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new ProjectQEDefinition();

        return INSTANCE;
    }
    @Override
    void addJPAAttributes(Map<String, AttributeJPAProcessingStrategy> validAttributes)
    {
        List<String> defaultFields = Arrays.asList("id", "title", "description", "createdAt");
        for(String defaultField : defaultFields)
        {
            validAttributes.put(defaultField, getJPADefaultStrategyByFieldName(defaultField));
        }


        validAttributes.put("collaboratorType", new ProjectCollaboratorJPAStrategy());

    }

    @Override
    void addJDBCAttributes(Map<String, AttributeJDBCProcessingStrategy> validAttributes)
    {
        List<String> defaultFields = Arrays.asList("id", "title", "description", "createdAt");
        for(String defaultField : defaultFields)
        {
            validAttributes.put(defaultField, getJDBCDefaultStrategyByFieldName(defaultField));
        }
        validAttributes.put("collaboratorType", new ProjectCollaboratorJDBCStrategy());
    }

    @Override
    void addRequiredArguments(Set<String> validAttributes)
    {
        validAttributes.add(AUTHOR_REQUESTER_ID);
    }

    @Override
    void addDefaultQueryExpression(Map<String, String> defaultAttributeMap)
    {
        defaultAttributeMap.put("collaboratorType", "collaboratorType eq \"ALL\"");
    }

    public String getAuthorRequesterIdKey()
    {
        return AUTHOR_REQUESTER_ID;
    }
}
