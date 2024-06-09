package com.github.kotooriiii.myworld.util.antlr.validation.arguments;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;

import java.util.HashMap;
import java.util.Map;

public abstract class ArgumentStep<U extends GenericModel, D extends BaseQEDefinition<U>> extends MyQueryExpression<U, D>
{
    private final Map<String, Object> argumentMap;
    public ArgumentStep(D definition)
    {
        super(definition);
        this.argumentMap = new HashMap<>();
    }


    public final QueryExpressionStep<U,D> step()
    {
        validateOrThrowException();
        return new QueryExpressionStep<U, D>(this, getDefinition());
    }

    public Map<String, Object> getArgumentMap()
    {
        return argumentMap;
    }

    public void putArgs(String key, Object value)
    {
        assert key != null;
        argumentMap.put(key,value);
    }

    private void validateOrThrowException() throws RuntimeException
    {
        if (!getDefinition().getRequiredArguments().containsAll(argumentMap.keySet()))
        {
            throw new RuntimeException("Developer: Please ensure that all required arguments are included.");
        }
    }
}
