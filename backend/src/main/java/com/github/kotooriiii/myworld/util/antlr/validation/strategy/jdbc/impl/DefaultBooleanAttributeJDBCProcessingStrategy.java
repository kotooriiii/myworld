package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.EqualJDBCCommand;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.JDBCCommand;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.NotEqualJDBCCommand;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultBooleanAttributeJDBCProcessingStrategy extends
        AttributeJDBCProcessingStrategy
{
    @Getter(lazy = true)
    private static final DefaultBooleanAttributeJDBCProcessingStrategy instance = new DefaultBooleanAttributeJDBCProcessingStrategy();
    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JDBCCommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, EqualJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.NE, NotEqualJDBCCommand.getInstance());
    }

    @Override
    public boolean supports(Object value)
    {
        return value instanceof Boolean;
    }
}
