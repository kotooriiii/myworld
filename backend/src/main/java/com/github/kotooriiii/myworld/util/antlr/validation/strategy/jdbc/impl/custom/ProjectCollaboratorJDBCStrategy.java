package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl.custom;

import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.JDBCCommand;
import com.github.kotooriiii.myworld.util.antlr.validation.fields.CollaboratorType;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectQEDefinition;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;
import org.apache.commons.lang3.EnumUtils;

import java.util.Map;

public class ProjectCollaboratorJDBCStrategy extends AttributeJDBCProcessingStrategy
{
    @Override
    protected boolean supports(Object value)
    {
        return value instanceof String && EnumUtils.isValidEnum(CollaboratorType.class, ((String) value).toUpperCase());
    }

    private <T extends Comparable<? super T>> String getStringBuilder(ExpressionJDBCVisitorImpl<?,?> visitor,
                                                                             ConditionalExpression<T> expression)
    {
        visitor.getInnerJoinBuilder().add("INNER JOIN " + "project_collaborators pc" + " ON " + "pc.project_id = id");

        CollaboratorType type = EnumUtils.getEnum(CollaboratorType.class, ((String) expression.getValue()).toUpperCase());

        Object author_requester_id = visitor.getSpecFactory().getArgumentStep().getArgumentMap().get(ProjectQEDefinition.AUTHOR_REQUESTER_ID);

        StringBuilder stringBuilder = new StringBuilder(" pc.author_id = '" + author_requester_id + "' AND ");
        return switch (type)
        {
            case SHARED  -> stringBuilder.append(" (pc.access_level = ").append(ProjectCollaborator.AccessLevel.EDITOR).append(" OR pc.access_level = ").append(ProjectCollaborator.AccessLevel.READ_ONLY).append(")").toString();

            case SELF -> stringBuilder.append(" (pc.access_level = ").append(ProjectCollaborator.AccessLevel.OWNER).append(")").toString();

            case ALL -> stringBuilder.append(" (TRUE)").toString();
        };
    }


    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JDBCCommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, new JDBCCommand()
        {
            @Override
            public <T extends Comparable<? super T>> void execute(ExpressionJDBCVisitorImpl<?,?> visitor,
                                                                  ConditionalExpression<T> expression)
            {
                visitor.getWhereClauseBuilder().append(getStringBuilder(visitor, expression));
            }
        });

        commandMap.put(ConditionalOperator.NE, new JDBCCommand()
        {
            @Override
            public <T extends Comparable<? super T>> void execute(ExpressionJDBCVisitorImpl<?,?> visitor,
                                                                  ConditionalExpression<T> expression)
            {
                visitor.getWhereClauseBuilder().append("NOT (").append(getStringBuilder(visitor, expression)).append(")");
            }
        });
    }
};