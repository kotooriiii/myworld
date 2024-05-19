package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.*;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultStringAttributeJDBCProcessingStrategy extends
        AttributeJDBCProcessingStrategy
{
    @Getter(lazy = true)
    private static final DefaultStringAttributeJDBCProcessingStrategy instance = new DefaultStringAttributeJDBCProcessingStrategy();

    @Override
    protected void addCommandDefinitions(Map<ConditionalOperator, JDBCCommand> commandMap)
    {
        commandMap.put(ConditionalOperator.EQ, EqualJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.NE, NotEqualJDBCCommand.getInstance());

        commandMap.put(ConditionalOperator.LT, LesserThanJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.GT, GreaterThanJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.LE, LesserOrEqualThanJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.GE, GreaterOrEqualThanJDBCCommand.getInstance());

        commandMap.put(ConditionalOperator.CO, ContainsJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.NC, NotContainsJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.SW, StartsWithJDBCCommand.getInstance());
        commandMap.put(ConditionalOperator.EW, EndsWithJDBCCommand.getInstance());
    }

    @Override
    public boolean supports(Object value)
    {
        return value instanceof String;
    }
}
