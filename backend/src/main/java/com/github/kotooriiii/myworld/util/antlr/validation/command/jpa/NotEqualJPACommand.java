package com.github.kotooriiii.myworld.util.antlr.validation.command.jpa;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotEqualJPACommand extends JPACommand
{
    @Getter(lazy = true)
    private static final NotEqualJPACommand instance = new NotEqualJPACommand();
    @Override
    public <T extends Comparable<? super T>> void execute(ExpressionJPAVisitorImpl<?> visitor, ConditionalExpression<T> expression)
    {
        Predicate predicate = visitor.getCriteriaBuilder().notEqual(visitor.getRoot().get(expression.getAttribute()), expression.getValue());
        visitor.getPredicateStack().push(predicate);
    }
}
