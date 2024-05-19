package com.github.kotooriiii.myworld.util.antlr.validation.factory;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.AttributeValidator;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;

public final class SpecFactory<U extends GenericModel>
{
    private final QueryExpression queryExpression;
    private final AttributeValidator<U> attributeValidator;
    private final boolean isDefaultAttribute;

    private SpecFactory(QueryExpression queryExpression, AttributeValidator<U> attributeValidator, boolean isDefaultAttribute)
    {
        this.queryExpression = queryExpression;
        this.attributeValidator = attributeValidator;
        this.isDefaultAttribute = isDefaultAttribute;
    }

    // Static factory method
    public static <U extends GenericModel> SpecFactory<U> createInstance(QueryExpression queryExpression, AttributeValidator<U> attributeValidator, boolean isDefaultAttribute)
    {
        return new SpecFactory<U>(queryExpression, attributeValidator, isDefaultAttribute);
    }

    public static <U extends GenericModel> SpecFactory<U> createInstance(QueryExpression queryExpression, AttributeValidator<U> attributeValidator)
    {
        return createInstance(queryExpression, attributeValidator, false);
    }

    public JPAResult<U> buildJPA()
    {
        //todo add default fields if not there.
        ExpressionJPAVisitorImpl<U> visitor = new ExpressionJPAVisitorImpl<>(queryExpression, attributeValidator);
        return visitor.build(isDefaultAttribute);
    }

    public JDBCResult buildJDBC()
    {
        ExpressionJDBCVisitorImpl<U> visitor = new ExpressionJDBCVisitorImpl<>(queryExpression, attributeValidator);
        return visitor.build(isDefaultAttribute);
    }
}
