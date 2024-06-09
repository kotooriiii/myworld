package com.github.kotooriiii.myworld.util.antlr.validation.arguments;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;

public class MyQueryExpression<U extends GenericModel, D extends BaseQEDefinition<U>>
{
    private final D definition;


    public MyQueryExpression(D definition)
    {
        this.definition = definition;
    }

    public D getDefinition()
    {
        return definition;
    }
}
