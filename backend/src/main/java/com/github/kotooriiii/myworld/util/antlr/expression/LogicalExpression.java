package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;
import lombok.Data;

@Data
public class LogicalExpression implements QueryExpression
{
    private final QueryExpression leftHand;
    private final LogicalOperator operator;
    private final QueryExpression rightHand;

    @Override
    public void accept(ExpressionVisitor<?,?> visitor) {
        visitor.visit(this);
    }
}
