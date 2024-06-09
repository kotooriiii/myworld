package com.github.kotooriiii.myworld.util.antlr.validation.factory;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.command.Command;
import com.github.kotooriiii.myworld.util.antlr.validation.result.DaoResult;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.AbstractStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class DaoInterface<T extends AbstractStrategy<?,?>, M extends DaoResult, V extends ExpressionVisitor<?, ?, ?>>
{

    private final Map<String, T> attributeStrategyMap;
    private final Map<String, M> defaultAttributeQueryExpressionMap;


    public DaoInterface()
    {
        this.attributeStrategyMap = new HashMap<>();
        this.defaultAttributeQueryExpressionMap = new HashMap<>();
    }

    public <U extends Comparable<? super U>> void checkExpressionValidOrThrow(
            ConditionalExpression<U> conditionalExpression)
            throws UnsupportedOperationException
    {
        AbstractStrategy<?,?> strategy = attributeStrategyMap.get(conditionalExpression.getAttribute());

        //Is the attribute valid?
        if (strategy == null)
        {
            throw new UnsupportedOperationException("The attribute name \"" + conditionalExpression.getAttribute() + "\" is not supported.");
        }

        Command<?> command = strategy.getCommandByConditionalOperator(conditionalExpression.getOperator());

        //The attribute allows this operator
        if (command == null)
        {
            throw new UnsupportedOperationException("The operator \"" + conditionalExpression.getOperator() + "\" is not supported by attribute \"" + conditionalExpression.getAttribute() + "\".");
        }

        //The attribute type must be supported.
        if (!strategy.supports(conditionalExpression.getOperator(), conditionalExpression.getValue()))
        {
            throw new UnsupportedOperationException("The attribute \"" + conditionalExpression.getAttribute() + "\" does not support comparisons with the specified data type \"" + conditionalExpression.getValue() + "\".");
        }

    }

    public void addDefaultAttributeMapping(String key, M daoResultMapping)
    {
        defaultAttributeQueryExpressionMap.put(key, daoResultMapping);
    }


    public T getProcessingStrategy(String attributeName)
    {

        return attributeStrategyMap.get(attributeName);
    }

    public Map<String, T> getAttributeStrategyMap()
    {
        return this.attributeStrategyMap;
    }

    public Map<String, M> getDefaultAttributeQueryExpressionMap()
    {
        return defaultAttributeQueryExpressionMap;
    }

    public abstract void processWithDefaultMappings(V visitor, Set<String> visitedAttributes);


}
