// Generated from FilterQuery.g4 by ANTLR 4.13.1

    package com.github.kotooriiii.myworld.util.antlr.grammar;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FilterQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, EQ=3, NE=4, GT=5, LT=6, GE=7, LE=8, CO=9, NC=10, SW=11, 
		EW=12, AND=13, OR=14, BOOLEAN=15, NULL=16, STRING=17, DOUBLE=18, LONG=19, 
		ATTNAME=20, WHITESPACE=21;
	public static final int
		RULE_query = 0, RULE_expression = 1, RULE_attrname = 2, RULE_value = 3, 
		RULE_conditionalOperator = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "expression", "attrname", "value", "conditionalOperator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "' '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "EQ", "NE", "GT", "LT", "GE", "LE", "CO", "NC", "SW", 
			"EW", "AND", "OR", "BOOLEAN", "NULL", "STRING", "DOUBLE", "LONG", "ATTNAME", 
			"WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FilterQuery.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FilterQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FilterQueryParser.EOF, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3145730L) != 0)) {
				{
				{
				setState(10);
				expression(0);
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupExpContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(FilterQueryParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(FilterQueryParser.WHITESPACE, i);
		}
		public GroupExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterGroupExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitGroupExp(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOrExpContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(FilterQueryParser.OR, 0); }
		public List<TerminalNode> WHITESPACE() { return getTokens(FilterQueryParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(FilterQueryParser.WHITESPACE, i);
		}
		public LogicalOrExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterLogicalOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitLogicalOrExp(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalExpContext extends ExpressionContext {
		public AttrnameContext attrname() {
			return getRuleContext(AttrnameContext.class,0);
		}
		public ConditionalOperatorContext conditionalOperator() {
			return getRuleContext(ConditionalOperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<TerminalNode> WHITESPACE() { return getTokens(FilterQueryParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(FilterQueryParser.WHITESPACE, i);
		}
		public ConditionalExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterConditionalExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitConditionalExp(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndExpContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(FilterQueryParser.AND, 0); }
		public List<TerminalNode> WHITESPACE() { return getTokens(FilterQueryParser.WHITESPACE); }
		public TerminalNode WHITESPACE(int i) {
			return getToken(FilterQueryParser.WHITESPACE, i);
		}
		public LogicalAndExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterLogicalAndExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitLogicalAndExp(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATTNAME:
				{
				_localctx = new ConditionalExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(19);
				attrname();
				setState(21); 
				_errHandler.sync(this);
				_alt = 1+1;
				do {
					switch (_alt) {
					case 1+1:
						{
						{
						setState(20);
						match(WHITESPACE);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(23); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(25);
				conditionalOperator();
				setState(27); 
				_errHandler.sync(this);
				_alt = 1+1;
				do {
					switch (_alt) {
					case 1+1:
						{
						{
						setState(26);
						match(WHITESPACE);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(29); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(31);
				value();
				}
				break;
			case T__0:
			case WHITESPACE:
				{
				_localctx = new GroupExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WHITESPACE) {
					{
					{
					setState(33);
					match(WHITESPACE);
					}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(39);
				match(T__0);
				setState(43);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(40);
						match(WHITESPACE);
						}
						} 
					}
					setState(45);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				setState(46);
				expression(0);
				setState(50);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(47);
						match(WHITESPACE);
						}
						} 
					}
					setState(52);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				setState(53);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(85);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(83);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new LogicalAndExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(57);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(59); 
						_errHandler.sync(this);
						_alt = 1+1;
						do {
							switch (_alt) {
							case 1+1:
								{
								{
								setState(58);
								match(WHITESPACE);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(61); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
						} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						setState(63);
						match(AND);
						setState(65); 
						_errHandler.sync(this);
						_alt = 1+1;
						do {
							switch (_alt) {
							case 1+1:
								{
								{
								setState(64);
								match(WHITESPACE);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(67); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
						} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						setState(69);
						expression(5);
						}
						break;
					case 2:
						{
						_localctx = new LogicalOrExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(70);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(72); 
						_errHandler.sync(this);
						_alt = 1+1;
						do {
							switch (_alt) {
							case 1+1:
								{
								{
								setState(71);
								match(WHITESPACE);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(74); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
						} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						setState(76);
						match(OR);
						setState(78); 
						_errHandler.sync(this);
						_alt = 1+1;
						do {
							switch (_alt) {
							case 1+1:
								{
								{
								setState(77);
								match(WHITESPACE);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(80); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
						} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						setState(82);
						expression(4);
						}
						break;
					}
					} 
				}
				setState(87);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttrnameContext extends ParserRuleContext {
		public TerminalNode ATTNAME() { return getToken(FilterQueryParser.ATTNAME, 0); }
		public AttrnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterAttrname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitAttrname(this);
		}
	}

	public final AttrnameContext attrname() throws RecognitionException {
		AttrnameContext _localctx = new AttrnameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_attrname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(ATTNAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	 
		public ValueContext() { }
		public void copyFrom(ValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanContext extends ValueContext {
		public TerminalNode BOOLEAN() { return getToken(FilterQueryParser.BOOLEAN, 0); }
		public BooleanContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitBoolean(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NullContext extends ValueContext {
		public TerminalNode NULL() { return getToken(FilterQueryParser.NULL, 0); }
		public NullContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterNull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitNull(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ValueContext {
		public TerminalNode STRING() { return getToken(FilterQueryParser.STRING, 0); }
		public StringContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitString(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoubleContext extends ValueContext {
		public TerminalNode DOUBLE() { return getToken(FilterQueryParser.DOUBLE, 0); }
		public DoubleContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterDouble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitDouble(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LongContext extends ValueContext {
		public TerminalNode LONG() { return getToken(FilterQueryParser.LONG, 0); }
		public LongContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterLong(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitLong(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_value);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				match(BOOLEAN);
				}
				break;
			case NULL:
				_localctx = new NullContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				match(NULL);
				}
				break;
			case STRING:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				match(STRING);
				}
				break;
			case DOUBLE:
				_localctx = new DoubleContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(93);
				match(DOUBLE);
				}
				break;
			case LONG:
				_localctx = new LongContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(94);
				match(LONG);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalOperatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(FilterQueryParser.EQ, 0); }
		public TerminalNode NE() { return getToken(FilterQueryParser.NE, 0); }
		public TerminalNode GT() { return getToken(FilterQueryParser.GT, 0); }
		public TerminalNode LT() { return getToken(FilterQueryParser.LT, 0); }
		public TerminalNode GE() { return getToken(FilterQueryParser.GE, 0); }
		public TerminalNode LE() { return getToken(FilterQueryParser.LE, 0); }
		public TerminalNode CO() { return getToken(FilterQueryParser.CO, 0); }
		public TerminalNode NC() { return getToken(FilterQueryParser.NC, 0); }
		public TerminalNode SW() { return getToken(FilterQueryParser.SW, 0); }
		public TerminalNode EW() { return getToken(FilterQueryParser.EW, 0); }
		public ConditionalOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).enterConditionalOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterQueryListener ) ((FilterQueryListener)listener).exitConditionalOperator(this);
		}
	}

	public final ConditionalOperatorContext conditionalOperator() throws RecognitionException {
		ConditionalOperatorContext _localctx = new ConditionalOperatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_conditionalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8184L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0015d\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0004\u0001\u0016\b\u0001"+
		"\u000b\u0001\f\u0001\u0017\u0001\u0001\u0001\u0001\u0004\u0001\u001c\b"+
		"\u0001\u000b\u0001\f\u0001\u001d\u0001\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001#\b\u0001\n\u0001\f\u0001&\t\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001*\b\u0001\n\u0001\f\u0001-\t\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u00011\b\u0001\n\u0001\f\u00014\t\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u00018\b\u0001\u0001\u0001\u0001\u0001\u0004\u0001<\b\u0001\u000b\u0001"+
		"\f\u0001=\u0001\u0001\u0001\u0001\u0004\u0001B\b\u0001\u000b\u0001\f\u0001"+
		"C\u0001\u0001\u0001\u0001\u0001\u0001\u0004\u0001I\b\u0001\u000b\u0001"+
		"\f\u0001J\u0001\u0001\u0001\u0001\u0004\u0001O\b\u0001\u000b\u0001\f\u0001"+
		"P\u0001\u0001\u0005\u0001T\b\u0001\n\u0001\f\u0001W\t\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003`\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\b\u0017\u001d"+
		"+2=CJP\u0001\u0002\u0005\u0000\u0002\u0004\u0006\b\u0000\u0001\u0001\u0000"+
		"\u0003\fo\u0000\r\u0001\u0000\u0000\u0000\u00027\u0001\u0000\u0000\u0000"+
		"\u0004X\u0001\u0000\u0000\u0000\u0006_\u0001\u0000\u0000\u0000\ba\u0001"+
		"\u0000\u0000\u0000\n\f\u0003\u0002\u0001\u0000\u000b\n\u0001\u0000\u0000"+
		"\u0000\f\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000"+
		"\r\u000e\u0001\u0000\u0000\u0000\u000e\u0010\u0001\u0000\u0000\u0000\u000f"+
		"\r\u0001\u0000\u0000\u0000\u0010\u0011\u0005\u0000\u0000\u0001\u0011\u0001"+
		"\u0001\u0000\u0000\u0000\u0012\u0013\u0006\u0001\uffff\uffff\u0000\u0013"+
		"\u0015\u0003\u0004\u0002\u0000\u0014\u0016\u0005\u0015\u0000\u0000\u0015"+
		"\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000\u0000\u0000\u0017"+
		"\u0018\u0001\u0000\u0000\u0000\u0017\u0015\u0001\u0000\u0000\u0000\u0018"+
		"\u0019\u0001\u0000\u0000\u0000\u0019\u001b\u0003\b\u0004\u0000\u001a\u001c"+
		"\u0005\u0015\u0000\u0000\u001b\u001a\u0001\u0000\u0000\u0000\u001c\u001d"+
		"\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001d\u001b"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000\u001f \u0003"+
		"\u0006\u0003\u0000 8\u0001\u0000\u0000\u0000!#\u0005\u0015\u0000\u0000"+
		"\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000"+
		"\u0000$%\u0001\u0000\u0000\u0000%\'\u0001\u0000\u0000\u0000&$\u0001\u0000"+
		"\u0000\u0000\'+\u0005\u0001\u0000\u0000(*\u0005\u0015\u0000\u0000)(\u0001"+
		"\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000"+
		"+)\u0001\u0000\u0000\u0000,.\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000"+
		"\u0000.2\u0003\u0002\u0001\u0000/1\u0005\u0015\u0000\u00000/\u0001\u0000"+
		"\u0000\u000014\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000020\u0001"+
		"\u0000\u0000\u000035\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u0000"+
		"56\u0005\u0002\u0000\u000068\u0001\u0000\u0000\u00007\u0012\u0001\u0000"+
		"\u0000\u00007$\u0001\u0000\u0000\u00008U\u0001\u0000\u0000\u00009;\n\u0004"+
		"\u0000\u0000:<\u0005\u0015\u0000\u0000;:\u0001\u0000\u0000\u0000<=\u0001"+
		"\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?A\u0005\r\u0000\u0000@B\u0005\u0015\u0000\u0000"+
		"A@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000ET\u0003\u0002"+
		"\u0001\u0005FH\n\u0003\u0000\u0000GI\u0005\u0015\u0000\u0000HG\u0001\u0000"+
		"\u0000\u0000IJ\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000JH\u0001"+
		"\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0005\u000e\u0000\u0000"+
		"MO\u0005\u0015\u0000\u0000NM\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000"+
		"\u0000PQ\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000QR\u0001\u0000"+
		"\u0000\u0000RT\u0003\u0002\u0001\u0004S9\u0001\u0000\u0000\u0000SF\u0001"+
		"\u0000\u0000\u0000TW\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000"+
		"UV\u0001\u0000\u0000\u0000V\u0003\u0001\u0000\u0000\u0000WU\u0001\u0000"+
		"\u0000\u0000XY\u0005\u0014\u0000\u0000Y\u0005\u0001\u0000\u0000\u0000"+
		"Z`\u0005\u000f\u0000\u0000[`\u0005\u0010\u0000\u0000\\`\u0005\u0011\u0000"+
		"\u0000]`\u0005\u0012\u0000\u0000^`\u0005\u0013\u0000\u0000_Z\u0001\u0000"+
		"\u0000\u0000_[\u0001\u0000\u0000\u0000_\\\u0001\u0000\u0000\u0000_]\u0001"+
		"\u0000\u0000\u0000_^\u0001\u0000\u0000\u0000`\u0007\u0001\u0000\u0000"+
		"\u0000ab\u0007\u0000\u0000\u0000b\t\u0001\u0000\u0000\u0000\u000e\r\u0017"+
		"\u001d$+27=CJPSU_";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}