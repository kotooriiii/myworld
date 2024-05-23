// Generated from FilterQuery.g4 by ANTLR 4.13.1

    package com.github.kotooriiii.myworld.util.antlr.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FilterQueryParser}.
 */
public interface FilterQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FilterQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(FilterQueryParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(FilterQueryParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGroupExp(FilterQueryParser.GroupExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGroupExp(FilterQueryParser.GroupExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExp(FilterQueryParser.LogicalOrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExp(FilterQueryParser.LogicalOrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conditionalExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExp(FilterQueryParser.ConditionalExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conditionalExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExp(FilterQueryParser.ConditionalExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExp(FilterQueryParser.LogicalAndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExp}
	 * labeled alternative in {@link FilterQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExp(FilterQueryParser.LogicalAndExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FilterQueryParser#attrname}.
	 * @param ctx the parse tree
	 */
	void enterAttrname(FilterQueryParser.AttrnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterQueryParser#attrname}.
	 * @param ctx the parse tree
	 */
	void exitAttrname(FilterQueryParser.AttrnameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(FilterQueryParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(FilterQueryParser.BooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code null}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNull(FilterQueryParser.NullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code null}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNull(FilterQueryParser.NullContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterString(FilterQueryParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitString(FilterQueryParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code double}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterDouble(FilterQueryParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code double}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitDouble(FilterQueryParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code long}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterLong(FilterQueryParser.LongContext ctx);
	/**
	 * Exit a parse tree produced by the {@code long}
	 * labeled alternative in {@link FilterQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitLong(FilterQueryParser.LongContext ctx);
	/**
	 * Enter a parse tree produced by {@link FilterQueryParser#conditionalOperator}.
	 * @param ctx the parse tree
	 */
	void enterConditionalOperator(FilterQueryParser.ConditionalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterQueryParser#conditionalOperator}.
	 * @param ctx the parse tree
	 */
	void exitConditionalOperator(FilterQueryParser.ConditionalOperatorContext ctx);
}