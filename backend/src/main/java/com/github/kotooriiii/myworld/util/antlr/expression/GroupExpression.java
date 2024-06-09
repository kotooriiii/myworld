package com.github.kotooriiii.myworld.util.antlr.expression;

import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class GroupExpression implements QueryExpression
{
    private final QueryExpression expression;

    @Override
    public void accept(ExpressionVisitor<?,?,?> visitor) {
        visitor.visit(this);
    }
}
