package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.EqualJPACommand;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.JPACommand;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jpa.NotEqualJPACommand;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultBooleanAttributeJPAProcessingStrategy extends
        AttributeJPAProcessingStrategy
{

    @Getter(lazy = true)
    private static final DefaultBooleanAttributeJPAProcessingStrategy instance = new DefaultBooleanAttributeJPAProcessingStrategy();
    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JPACommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, EqualJPACommand.getInstance());
        commandMap.put(ConditionalOperator.NE, NotEqualJPACommand.getInstance());
    }

    @Override
    public boolean supports(Object value)
    {
        return value instanceof Boolean;
    }
}
