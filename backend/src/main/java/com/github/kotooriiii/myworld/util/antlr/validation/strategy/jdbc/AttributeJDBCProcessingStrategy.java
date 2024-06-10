package com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc;

import com.github.kotooriiii.myworld.util.antlr.validation.command.jdbc.JDBCCommand;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.AbstractStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;

public abstract class AttributeJDBCProcessingStrategy extends AbstractStrategy<ExpressionJDBCVisitorImpl<?,?>, JDBCCommand>
{
}