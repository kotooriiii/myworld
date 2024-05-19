package com.github.kotooriiii.myworld.util.antlr.validation.command.jpa;

import com.github.kotooriiii.myworld.util.antlr.expression.ConditionalExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotContainsJPACommand extends JPACommand
{
    @Getter(lazy = true)
    private static final NotContainsJPACommand instance = new NotContainsJPACommand();
    @Override
    public <T extends Comparable<? super T>> void execute(ExpressionJPAVisitorImpl<?> visitor, ConditionalExpression<T> expression)
    {
        Predicate predicate = visitor.getCriteriaBuilder().notLike(
                visitor.getRoot().get(expression.getAttribute()),
                "%" + getSanitizedString(expression.getValue().toString()) + "%", getEscapeSuffix()
        );
        visitor.getPredicateStack().push(predicate);
    }
}

