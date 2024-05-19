package com.github.kotooriiii.myworld.util.antlr.util;

import com.github.kotooriiii.myworld.util.antlr.QueryParser;
import com.github.kotooriiii.myworld.util.antlr.exception.BadRequestException;
import com.github.kotooriiii.myworld.util.antlr.exception.QueryParseException;
import com.github.kotooriiii.myworld.util.antlr.expression.GroupExpression;
import com.github.kotooriiii.myworld.util.antlr.expression.NoOpExpression;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import org.apache.commons.lang3.StringUtils;

public class ExpressionUtils {
    public static QueryExpression getFilterQueryExpression(String filterQuery) {

        QueryExpression expression = null;

        final QueryParser parser = new QueryParser();

        try
        {
            expression = parser.parse(filterQuery);
        } catch (QueryParseException e)
        {
            throw new BadRequestException("Oops! Looks like there was a hiccup while parsing the grammar. Could you double-check to ensure that the grammar is syntactically correct? If the issue persists, feel free to reach out for further assistance. Thanks for your understanding!", e);
        }

        //If filterQuery is empty or null, we need a no-op exp
        if(expression == null)
        {
            expression = new NoOpExpression();
        }

        return expression;
    }
}
