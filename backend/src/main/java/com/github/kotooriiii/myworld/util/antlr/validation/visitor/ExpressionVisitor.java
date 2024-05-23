package com.github.kotooriiii.myworld.util.antlr.validation.visitor;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.*;
import com.github.kotooriiii.myworld.util.antlr.validation.result.DaoResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.AttributeValidator;

import java.util.HashSet;
import java.util.Set;

public abstract class ExpressionVisitor<U extends GenericModel, R extends DaoResult>
{
    protected final QueryExpression queryExpression;
    protected final AttributeValidator<U> attributeValidator;
    protected final Set<String> visitedAttributes;
    private boolean isNoOp = false;



    public ExpressionVisitor(AttributeValidator<U> attributeValidator, QueryExpression queryExpression)
    {
        this.attributeValidator = attributeValidator;
        this.queryExpression = queryExpression;
        this.visitedAttributes = new HashSet<>();
    }
    public abstract <T extends Comparable<? super T>> void visit(ConditionalExpression<T> expression);
    public abstract void visit(ConditionalOperator conditionalOperator);

    public abstract void visit(LogicalExpression expression);
    public abstract void visit(LogicalOperator logicalOperator);


    public abstract void visit(GroupExpression expression);

    public void visit(NoOpExpression noOpExpression)
    {
        isNoOp = true;
    }
    public final boolean isNoOp()
    {
        return isNoOp;
    }
    public abstract R build(boolean isDefaultAttribute);


}