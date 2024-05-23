package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.custom;

import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.JPACommand;
import com.github.kotooriiii.myworld.util.antlr.validation.fields.CollaboratorType;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.EnumUtils;

import java.util.Map;

public class ProjectCollaboratorJPAStrategy extends AttributeJPAProcessingStrategy
{
    @Override
    protected boolean supports(Object value)
    {
        return value instanceof String && EnumUtils.isValidEnum(CollaboratorType.class, ((String) value).toUpperCase());
    }

    private <T extends Comparable<? super T>> Predicate getPredicate(ExpressionJPAVisitorImpl<?> visitor,
                                                                     ConditionalExpression<T> expression)
    {
        Join<?, ProjectCollaborator> projectCollaborators = visitor.getRoot().join("projectCollaborators");

        CollaboratorType type = EnumUtils.getEnum(CollaboratorType.class, ((String) expression.getValue()).toUpperCase());

        return switch (type)
        {
            case SHARED -> visitor.getCriteriaBuilder().or(
                    visitor.getCriteriaBuilder().equal(projectCollaborators.get("accessLevel"), ProjectCollaborator.AccessLevel.EDITOR),
                    visitor.getCriteriaBuilder().equal(projectCollaborators.get("accessLevel"), ProjectCollaborator.AccessLevel.READ_ONLY)
            );
            case SELF ->
                    visitor.getCriteriaBuilder().equal(projectCollaborators.get("accessLevel"), ProjectCollaborator.AccessLevel.OWNER);

            case ALL -> visitor.getCriteriaBuilder().and(); //CriteriaBuilder.and() returns an always true predicate.
        };
    }

    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JPACommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, new JPACommand()
        {
            @Override
            public <T extends Comparable<? super T>> void execute(ExpressionJPAVisitorImpl<?> visitor,
                                                                  ConditionalExpression<T> expression)
            {
                Predicate predicate = getPredicate(visitor, expression);
                visitor.getPredicateStack().push(predicate);
            }
        });

        commandMap.put(ConditionalOperator.NE, new JPACommand()
        {
            @Override
            public <T extends Comparable<? super T>> void execute(ExpressionJPAVisitorImpl<?> visitor,
                                                                  ConditionalExpression<T> expression)
            {
                Predicate predicate = getPredicate(visitor, expression).not();
                visitor.getPredicateStack().push(predicate);
            }
        });

    }
};