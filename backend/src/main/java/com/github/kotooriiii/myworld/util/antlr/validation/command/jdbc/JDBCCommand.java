package com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc;

import com.github.kotooriiii.myworld.util.antlr.validation.command.Command;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;

public abstract class JDBCCommand implements Command<ExpressionJDBCVisitorImpl<?>>
{

    public String getSanitizedStringOfKey(String key)
    {
        return "REPLACE(REPLACE(REPLACE(" + ":" + key + ", '\\', '\\\\'), '%', '\\%'), '_', '\\_')";
    }


    @Override
    public String getSanitizedString(String value)
    {
        return getSanitizedStringOfKey("");
    }


    protected String getFinalEscapeSuffix()
    {
       return "ESCAPE " + getEscapeSuffix();
    }

    @Override
    public Character getEscapeSuffix()
    {
        return '\\';
    }


}
