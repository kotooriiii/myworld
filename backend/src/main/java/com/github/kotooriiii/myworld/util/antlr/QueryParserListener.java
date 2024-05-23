package com.github.kotooriiii.myworld.util.antlr;

import com.github.kotooriiii.myworld.util.DateUtils;
import com.github.kotooriiii.myworld.util.antlr.expression.*;
import com.github.kotooriiii.myworld.util.antlr.grammar.FilterQueryBaseListener;
import com.github.kotooriiii.myworld.util.antlr.grammar.FilterQueryParser;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;

public class QueryParserListener extends FilterQueryBaseListener
{
    private String attribute;
    private ConditionalOperator conditionalOperator;
    private Comparable<?> value;

    private final Deque<QueryExpression> expressionQueue;

    public QueryParserListener() {
        expressionQueue = new LinkedList<QueryExpression>();
    }

    public QueryExpression getExpression() { return expressionQueue.element(); }

    public void reset() {
        attribute = null;
        conditionalOperator = null;
        value = null;
        expressionQueue.clear();
    }

    @Override
    public void exitGroupExp(FilterQueryParser.GroupExpContext ctx) {
        final GroupExpression groupExpression = new GroupExpression(expressionQueue.removeLast());
        expressionQueue.add(groupExpression);
    }

    @Override
    public void exitConditionalExp(FilterQueryParser.ConditionalExpContext ctx) {

        if (value instanceof Long)
        {
            expressionQueue.add(new ConditionalExpression<Long>(attribute, conditionalOperator, (Long) value));

        }
        else if (value instanceof String)
        {

            //Check if it is a date object.
            LocalDateTime localDateTime = DateUtils.parseToLocalDateTime(String.valueOf(value));
            if(localDateTime != null)
            {
                expressionQueue.add(new ConditionalExpression<LocalDateTime>(attribute, conditionalOperator, localDateTime));
            }
            else
            {
                expressionQueue.add(new ConditionalExpression<String>(attribute, conditionalOperator, (String) value));
            }
        }
        else if(value instanceof Double)
        {
            expressionQueue.add(new ConditionalExpression<Double>(attribute, conditionalOperator, (Double) value));
        }
        else if(value instanceof Boolean)
        {
            expressionQueue.add(new ConditionalExpression<Boolean>(attribute, conditionalOperator, (Boolean) value));
        }
        else {
            expressionQueue.add(new ConditionalExpression<String>(attribute, conditionalOperator, null)); //todo does null work?
        }

        attribute = null;
        conditionalOperator = null;
        value = null;
    }

    @Override
    public void exitLogicalAndExp(FilterQueryParser.LogicalAndExpContext ctx) {

        final LogicalExpression logicalExpression = new LogicalExpression(expressionQueue.removeFirst(), LogicalOperator.AND, expressionQueue.removeFirst());
        expressionQueue.addLast(logicalExpression);
    }

    @Override
    public void exitLogicalOrExp(FilterQueryParser.LogicalOrExpContext ctx) {
        final LogicalExpression logicalExpression = new LogicalExpression(expressionQueue.removeFirst(), LogicalOperator.OR, expressionQueue.removeFirst());
        expressionQueue.addLast(logicalExpression);
    }

    @Override
    public void exitAttrname(FilterQueryParser.AttrnameContext ctx) {
        attribute = ctx.getText();
    }

    @Override
    public void exitBoolean(FilterQueryParser.BooleanContext ctx) {
        value = parseBoolean(ctx.getText());
    }

    @Override
    public void exitDouble(FilterQueryParser.DoubleContext ctx) {
        value = (Double) Double.parseDouble(ctx.getText());
    }

    @Override
    public void exitString(FilterQueryParser.StringContext ctx) {
        value = ctx.getText().replace("\"", "");
    }

    @Override
    public void exitLong(FilterQueryParser.LongContext ctx) {
        value = Long.parseLong(ctx.getText());
    }

    @Override
    public void exitNull(FilterQueryParser.NullContext ctx) {
        value = null;
    }

    @Override
    public void exitConditionalOperator(FilterQueryParser.ConditionalOperatorContext ctx) {
        conditionalOperator = ConditionalOperator.valueOf(ctx.getText().toUpperCase());
    }

    private static Boolean parseBoolean(String str) {
        Boolean value = Boolean.FALSE;

        if (str != null && (str = str.trim()).length() > 0) {
            String upper = str.toUpperCase();
            value = (Boolean.valueOf(upper.equals("TRUE") || upper.equals("YES") || upper.equals("ON") || upper.equals("T") || upper.equals("Y")));
        }

        return value;
    }
}

