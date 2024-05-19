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

@Component
public class ProjectValidator extends AttributeValidator<Project>
{
    public ProjectValidator() {
        super(Project.class);
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
    void addDefaultQueryExpression(Map<String, String> defaultAttributeMap)
    {
        defaultAttributeMap.put("collaboratorType", "collaboratorType eq \"ALL\"");
    }


}
