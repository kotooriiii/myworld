package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;

public interface QueryExpression
{
    public abstract void accept(ExpressionVisitor<?, ?> visitor);
}
