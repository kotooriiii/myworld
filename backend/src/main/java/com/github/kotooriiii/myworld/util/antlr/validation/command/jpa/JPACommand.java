package com.github.kotooriiii.myworld.util.antlr.validation.command.jpa;

import com.github.kotooriiii.myworld.util.antlr.validation.command.Command;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;

public abstract class JPACommand implements Command<ExpressionJPAVisitorImpl<?>>
{
    @Override
    public String getSanitizedString(String value)
    {
        return value.replace("\\", "\\\\").replace("%", "\\%").replace("_","\\_");
    }
    @Override
    public Character getEscapeSuffix()
    {
        return '\\';
    }


}
