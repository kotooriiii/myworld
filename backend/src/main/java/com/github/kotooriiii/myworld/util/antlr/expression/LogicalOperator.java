package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogicalOperator implements QueryExpression
{
    AND,
    OR;

    @Override
    public void accept(ExpressionVisitor<?,?,?> visitor)
    {
        visitor.visit(this);
    }
}
