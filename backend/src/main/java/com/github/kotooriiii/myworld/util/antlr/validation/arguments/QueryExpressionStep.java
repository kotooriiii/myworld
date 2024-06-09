package com.github.kotooriiii.myworld.util.antlr.validation.arguments;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;
import com.github.kotooriiii.myworld.util.antlr.validation.factory.SpecFactory;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;

public class QueryExpressionStep<U extends GenericModel, D extends BaseQEDefinition<U>>
{

    private final D definition;
    private final ArgumentStep<U, D> argumentStep;

    public QueryExpressionStep(ArgumentStep<U, D> argumentStep, D definition)
    {
        this.argumentStep = argumentStep;
        this.definition = definition;
    }

    public final SpecFactory<U,D> withQueryExpression(QueryExpression queryExpression)
    {
        definition.getDefaultAttributeMap().forEach((s, s2) ->
        {
            SpecFactory<U,D> uSpecFactory = SpecFactory.createInstance(argumentStep, ExpressionUtils.getFilterQueryExpression(s2), definition, true);
            definition.withJPA().addDefaultAttributeMapping(s, uSpecFactory.buildJPA());
            definition.withJDBC().addDefaultAttributeMapping(s, uSpecFactory.buildJDBC());
        });

          return SpecFactory.createInstance(argumentStep, queryExpression, definition);
    }
}
