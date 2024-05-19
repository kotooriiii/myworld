package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConditionalOperator implements QueryExpression
{
    EQ,
    NE,

    LT,
    GT,
    LE,
    GE,

    CO,
    NC,
    SW,
    EW;

    @Override
    public void accept(ExpressionVisitor<?,?> visitor) {
        visitor.visit(this);
    }
}
