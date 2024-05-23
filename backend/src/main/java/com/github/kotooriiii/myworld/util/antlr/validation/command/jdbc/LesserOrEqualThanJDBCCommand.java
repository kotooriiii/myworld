package com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LesserOrEqualThanJDBCCommand extends JDBCCommand
{
    @Getter(lazy = true)
    private static final LesserOrEqualThanJDBCCommand instance = new LesserOrEqualThanJDBCCommand();

    @Override
    public <T extends Comparable<? super T>> void execute(ExpressionJDBCVisitorImpl<?> visitor, ConditionalExpression<T> expression)
    {

        //Attribute
        visitor.getWhereClauseBuilder().append(expression.getAttribute());

        //Operator
        visitor.getWhereClauseBuilder().append(" <=")
                .append(" ")
                .append(getSanitizedStringOfKey(expression.getAttribute()));

        //Value
        visitor.getValueMap().putIfAbsent(expression.getAttribute(), expression.getValue());
    }
}
