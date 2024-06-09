package com.github.kotooriiii.myworld.util.antlr.validation.command;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionVisitor;

public interface Command<V extends ExpressionVisitor<?,?,?>>
{
    String getSanitizedString(String value);

    Character getEscapeSuffix();

    public abstract <T extends Comparable<? super T>> void execute(V visitor, ConditionalExpression<T> expression);


}
