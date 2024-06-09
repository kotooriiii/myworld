package com.github.kotooriiii.myworld.util.antlr.validation.factory;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.arguments.ArgumentStep;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;

public final class SpecFactory<U extends GenericModel, D extends BaseQEDefinition<U>>
{
    private final QueryExpression queryExpression;
    private final D baseQEDefinition;
    private final ArgumentStep<U, D> argumentStep;
    private final boolean isDefaultAttribute;

    public SpecFactory(QueryExpression queryExpression, D baseQEDefinition,
                                                       ArgumentStep<U, D> argumentStep, boolean isDefaultAttribute)
    {
        this.queryExpression = queryExpression;
        this.baseQEDefinition = baseQEDefinition;
        this.argumentStep = argumentStep;
        this.isDefaultAttribute = isDefaultAttribute;
    }


    // Static factory method
    public static <U extends GenericModel, D extends BaseQEDefinition<U>> SpecFactory<U,D> createInstance(ArgumentStep<U, D> argumentStep,
            QueryExpression queryExpression, D baseQEDefinition, boolean isDefaultAttribute)
    {
        return new SpecFactory<U,D>(queryExpression, baseQEDefinition, argumentStep, isDefaultAttribute);
    }

    public static <U extends GenericModel, D extends BaseQEDefinition<U>> SpecFactory<U,D> createInstance(
            ArgumentStep<U, D> argumentStep, QueryExpression queryExpression, D definition)
    {
        return createInstance(argumentStep, queryExpression, definition, false);
    }


    public JPAResult<U> buildJPA()
    {
        //todo add default fields if not there.
        ExpressionJPAVisitorImpl<U,D> visitor = new ExpressionJPAVisitorImpl<>(this);
        return visitor.build(isDefaultAttribute);
    }

    public JDBCResult buildJDBC()
    {
        ExpressionJDBCVisitorImpl<U,D> visitor = new ExpressionJDBCVisitorImpl<>(this);
        return visitor.build(isDefaultAttribute);
    }

    public QueryExpression getQueryExpression()
    {
        return queryExpression;
    }

    public ArgumentStep<U, D> getArgumentStep()
    {
        return argumentStep;
    }

    public D getBaseQEDefinition()
    {
        return baseQEDefinition;
    }
}
