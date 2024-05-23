package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.*;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultStringAttributeJPAProcessingStrategy extends
        AttributeJPAProcessingStrategy
{
    @Getter(lazy = true)
    private static final DefaultStringAttributeJPAProcessingStrategy instance = new DefaultStringAttributeJPAProcessingStrategy();

    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JPACommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, EqualJPACommand.getInstance());
        commandMap.put(ConditionalOperator.NE, NotEqualJPACommand.getInstance());

        commandMap.put(ConditionalOperator.LT, LesserThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.GT, GreaterThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.LE, LesserOrEqualThanJPACommand.getInstance());
        commandMap.put(ConditionalOperator.GE, GreaterOrEqualThanJPACommand.getInstance());

        commandMap.put(ConditionalOperator.CO, ContainsJPACommand.getInstance());
        commandMap.put(ConditionalOperator.NC, NotContainsJPACommand.getInstance());
        commandMap.put(ConditionalOperator.SW, StartsWithJPACommand.getInstance());
        commandMap.put(ConditionalOperator.EW, EndsWithJPACommand.getInstance());
    }

    @Override
    public boolean supports(Object value)
    {
        return value instanceof String;
    }
}
