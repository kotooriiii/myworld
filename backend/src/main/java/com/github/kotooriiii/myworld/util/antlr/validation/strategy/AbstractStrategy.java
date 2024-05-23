package com.github.kotooriiii.myworld.util.antlr.validation.strategy;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalOperator;
import com.github.kotooriiii.myworld.util.antlr.validation.command.Command;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStrategy<V extends ExpressionVisitor<?,?>, C extends Command<V>>
{
    private final Map<ConditionalOperator, C> commandMap;

    public AbstractStrategy()
    {
        this.commandMap = new HashMap<>();
        addCommandDefinitions(commandMap);
    }

    protected abstract void addCommandDefinitions(Map<ConditionalOperator, C> commandMap);

    protected abstract boolean supports(Object value);

    public boolean supports(ConditionalOperator operator, Object value)
    {
        if(value == null && (operator.equals(ConditionalOperator.EQ) || operator.equals(ConditionalOperator.NE)))
            return true;
        return supports(value);
    }
    public <T extends Comparable<? super T>> void process(V visitor, ConditionalExpression<T> expression)
    {
        C commandByConditionalOperator = getCommandByConditionalOperator(expression.getOperator());
        commandByConditionalOperator.execute(visitor, expression);
    }

    public C getCommandByConditionalOperator(ConditionalOperator conditionalOperator)
    {
        return commandMap.get(conditionalOperator);
    }

    protected Map<ConditionalOperator, C> getCommandMap()
    {
        return commandMap;
    }


}
