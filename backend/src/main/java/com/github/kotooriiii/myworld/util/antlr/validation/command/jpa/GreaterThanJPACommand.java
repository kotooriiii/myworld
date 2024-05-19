package com.github.kotooriiii.myworld.util.antlr.validation.command.jpa;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GreaterThanJPACommand extends JPACommand
{
    @Getter(lazy = true)
    private static final GreaterThanJPACommand instance = new GreaterThanJPACommand();
    @Override
    public <T extends Comparable<? super T>> void execute(ExpressionJPAVisitorImpl<?> visitor, ConditionalExpression<T> expression)
    {
        Predicate predicate = visitor.getCriteriaBuilder().greaterThan(visitor.getRoot().get(expression.getAttribute()), expression.getValue());
        visitor.getPredicateStack().push(predicate);
    }
}
