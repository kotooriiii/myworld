package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;
import lombok.Data;

@Data
public class ConditionalExpression<T extends Comparable<? super T>> implements QueryExpression
{
    private final String attribute;
    private final ConditionalOperator operator;
    private final T value;

    @Override
    public void accept(ExpressionVisitor<?,?> visitor) {
        visitor.visit(this);
    }
}
