package com.github.kotooriiii.myworld.util.antlr.validation.visitor;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.*;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.AttributeValidator;

import java.util.*;

public class ExpressionJDBCVisitorImpl<U extends GenericModel> extends ExpressionVisitor<U,JDBCResult>
{
    private final StringBuilder whereClauseBuilder;
    private final LinkedHashSet<String> innerJoinBuilder;
    private final Map<String, Object> valueMap;

    public ExpressionJDBCVisitorImpl(QueryExpression queryExpression, AttributeValidator<U> attributeValidator)
    {
        super(attributeValidator, queryExpression);
        whereClauseBuilder = new StringBuilder();
        innerJoinBuilder = new LinkedHashSet<>();
        valueMap = new HashMap<>();
    }

    @Override
    public <T extends Comparable<? super T>> void visit(ConditionalExpression<T> expression)
    {
        
        this.attributeValidator.withJDBC().checkExpressionValidOrThrow(expression);
        this.attributeValidator.withJDBC().getProcessingStrategy(expression.getAttribute()).process(this, expression);
        visitedAttributes.add(expression.getAttribute());

    }

    @Override
    public void visit(ConditionalOperator conditionalOperator)
    {
        //defined with visit(ConditionalExpression)
        //do not implement here!
    }

    @Override
    public void visit(LogicalExpression expression)
    {
        whereClauseBuilder.append(' ');
        expression.getLeftHand().accept(this);
        whereClauseBuilder.append(' ');
        expression.getOperator().accept(this);
        whereClauseBuilder.append(' ');
        expression.getRightHand().accept(this);
    }

    @Override
    public void visit(LogicalOperator logicalOperator)
    {
        switch (logicalOperator)
        {
            case AND -> whereClauseBuilder.append("AND");
            case OR -> whereClauseBuilder.append("OR");
        }
    }

    @Override
    public void visit(GroupExpression expression)
    {
        whereClauseBuilder.append(' ');
        whereClauseBuilder.append("(");
        whereClauseBuilder.append(' ');
        expression.getExpression().accept(this);
        whereClauseBuilder.append(' ');
        whereClauseBuilder.append(")");
    }



    public LinkedHashSet<String> getInnerJoinBuilder()
    {
        return innerJoinBuilder;
    }

    public StringBuilder getWhereClauseBuilder()
    {
        return whereClauseBuilder;
    }

    public Map<String, Object> getValueMap()
    {
        return valueMap;
    }

    @Override
    public JDBCResult build(boolean isDefaultAttribute)
    {
        whereClauseBuilder.append("(");
        whereClauseBuilder.append(' ');
        queryExpression.accept(this);
        whereClauseBuilder.append(' ');

        if(!isDefaultAttribute)
        {
            this.attributeValidator.withJDBC().processWithDefaultMappings(this, visitedAttributes);
            whereClauseBuilder.append(' ');
        }

        whereClauseBuilder.append(")");

        return new JDBCResult(String.join("\n", innerJoinBuilder), whereClauseBuilder.toString(), new HashMap<>(valueMap));
    }
}
