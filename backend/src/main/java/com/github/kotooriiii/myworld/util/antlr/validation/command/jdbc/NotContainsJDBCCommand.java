package com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotContainsJDBCCommand extends JDBCCommand
{
    @Getter(lazy = true)
    private static final NotContainsJDBCCommand instance = new NotContainsJDBCCommand();

    @Override
    public <T extends Comparable<? super T>> void execute(ExpressionJDBCVisitorImpl<?,?> visitor, ConditionalExpression<T> expression)
    {

        //Attribute
        visitor.getWhereClauseBuilder().append(expression.getAttribute());

        //Operator
        visitor.getWhereClauseBuilder().append(" NOT LIKE")
                .append(" ")
                .append("CONCAT(CONCAT('%',").append(getSanitizedStringOfKey(expression.getAttribute())).append("), '%') ").append(getFinalEscapeSuffix());

        //Value
        visitor.getValueMap().putIfAbsent(expression.getAttribute(), expression.getValue());
    }
}

