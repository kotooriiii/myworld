package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.*;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultNumberOrTimeAttributeJPAProcessingStrategy extends
        AttributeJPAProcessingStrategy
{
    @Getter(lazy = true)
    private static final DefaultNumberOrTimeAttributeJPAProcessingStrategy instance =  new DefaultNumberOrTimeAttributeJPAProcessingStrategy();

    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JPACommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, EqualJPACommand.getInstance());
        commandMap.put(ConditionalOperator.NE, NotEqualJPACommand.getInstance());

        commandMap.put(ConditionalOperator.LT, LesserThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.GT, GreaterThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.LE, LesserOrEqualThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.GE, GreaterOrEqualThanJPACommand.getInstance());
    }

    @Override
    public boolean supports(Object value)
    {
        return value instanceof Number || value instanceof LocalDateTime || value instanceof LocalDate;
    }
}
