// Generated from FilterQuery.g4 by ANTLR 4.13.1

    package com.github.kotooriiii.myworld.util.antlr.grammar;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FilterQueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, EQ=3, NE=4, GT=5, LT=6, GE=7, LE=8, CO=9, NC=10, SW=11, 
		EW=12, AND=13, OR=14, BOOLEAN=15, NULL=16, STRING=17, DOUBLE=18, LONG=19, 
		ATTNAME=20, WHITESPACE=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "EQ", "NE", "GT", "LT", "GE", "LE", "CO", "NC", "SW", 
			"EW", "AND", "OR", "BOOLEAN", "NULL", "STRING", "DOUBLE", "LONG", "ATTNAME", 
			"WHITESPACE", "INT", "EXP", "ESC", "UNICODE", "HEX"
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


	public FilterQueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FilterQuery.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0015\u00b5\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000eh\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010"+
		"r\b\u0010\n\u0010\f\u0010u\t\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0003\u0011z\b\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0004\u0011"+
		"\u007f\b\u0011\u000b\u0011\f\u0011\u0080\u0001\u0011\u0003\u0011\u0084"+
		"\b\u0011\u0001\u0012\u0003\u0012\u0087\b\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0003\u0012\u008d\b\u0012\u0001\u0012\u0003\u0012"+
		"\u0090\b\u0012\u0001\u0013\u0004\u0013\u0093\b\u0013\u000b\u0013\f\u0013"+
		"\u0094\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0005"+
		"\u0015\u009c\b\u0015\n\u0015\f\u0015\u009f\t\u0015\u0003\u0015\u00a1\b"+
		"\u0015\u0001\u0016\u0001\u0016\u0003\u0016\u00a5\b\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u00ac\b\u0017\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0019\u0001\u0019\u0000\u0000\u001a\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014)\u0015+\u0000-\u0000/\u00001\u00003\u0000\u0001\u0000"+
		"\u0016\u0002\u0000EEee\u0002\u0000QQqq\u0002\u0000NNnn\u0002\u0000GGg"+
		"g\u0002\u0000TTtt\u0002\u0000LLll\u0002\u0000CCcc\u0002\u0000OOoo\u0002"+
		"\u0000SSss\u0002\u0000WWww\u0002\u0000AAaa\u0002\u0000DDdd\u0002\u0000"+
		"RRrr\u0002\u0000UUuu\u0002\u0000FFff\u0002\u0000\"\"\\\\\u0001\u00000"+
		"9\u0005\u0000-.0:AZ__az\u0001\u000019\u0002\u0000++--\b\u0000\"\"//\\"+
		"\\bbffnnrrtt\u0003\u000009AFaf\u00bd\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f"+
		"\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000"+
		"\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000"+
		"\u0000\u0000)\u0001\u0000\u0000\u0000\u00015\u0001\u0000\u0000\u0000\u0003"+
		"7\u0001\u0000\u0000\u0000\u00059\u0001\u0000\u0000\u0000\u0007<\u0001"+
		"\u0000\u0000\u0000\t?\u0001\u0000\u0000\u0000\u000bB\u0001\u0000\u0000"+
		"\u0000\rE\u0001\u0000\u0000\u0000\u000fH\u0001\u0000\u0000\u0000\u0011"+
		"K\u0001\u0000\u0000\u0000\u0013N\u0001\u0000\u0000\u0000\u0015Q\u0001"+
		"\u0000\u0000\u0000\u0017T\u0001\u0000\u0000\u0000\u0019W\u0001\u0000\u0000"+
		"\u0000\u001b[\u0001\u0000\u0000\u0000\u001dg\u0001\u0000\u0000\u0000\u001f"+
		"i\u0001\u0000\u0000\u0000!n\u0001\u0000\u0000\u0000#y\u0001\u0000\u0000"+
		"\u0000%\u008f\u0001\u0000\u0000\u0000\'\u0092\u0001\u0000\u0000\u0000"+
		")\u0096\u0001\u0000\u0000\u0000+\u00a0\u0001\u0000\u0000\u0000-\u00a2"+
		"\u0001\u0000\u0000\u0000/\u00a8\u0001\u0000\u0000\u00001\u00ad\u0001\u0000"+
		"\u0000\u00003\u00b3\u0001\u0000\u0000\u000056\u0005(\u0000\u00006\u0002"+
		"\u0001\u0000\u0000\u000078\u0005)\u0000\u00008\u0004\u0001\u0000\u0000"+
		"\u00009:\u0007\u0000\u0000\u0000:;\u0007\u0001\u0000\u0000;\u0006\u0001"+
		"\u0000\u0000\u0000<=\u0007\u0002\u0000\u0000=>\u0007\u0000\u0000\u0000"+
		">\b\u0001\u0000\u0000\u0000?@\u0007\u0003\u0000\u0000@A\u0007\u0004\u0000"+
		"\u0000A\n\u0001\u0000\u0000\u0000BC\u0007\u0005\u0000\u0000CD\u0007\u0004"+
		"\u0000\u0000D\f\u0001\u0000\u0000\u0000EF\u0007\u0003\u0000\u0000FG\u0007"+
		"\u0000\u0000\u0000G\u000e\u0001\u0000\u0000\u0000HI\u0007\u0005\u0000"+
		"\u0000IJ\u0007\u0000\u0000\u0000J\u0010\u0001\u0000\u0000\u0000KL\u0007"+
		"\u0006\u0000\u0000LM\u0007\u0007\u0000\u0000M\u0012\u0001\u0000\u0000"+
		"\u0000NO\u0007\u0002\u0000\u0000OP\u0007\u0006\u0000\u0000P\u0014\u0001"+
		"\u0000\u0000\u0000QR\u0007\b\u0000\u0000RS\u0007\t\u0000\u0000S\u0016"+
		"\u0001\u0000\u0000\u0000TU\u0007\u0000\u0000\u0000UV\u0007\t\u0000\u0000"+
		"V\u0018\u0001\u0000\u0000\u0000WX\u0007\n\u0000\u0000XY\u0007\u0002\u0000"+
		"\u0000YZ\u0007\u000b\u0000\u0000Z\u001a\u0001\u0000\u0000\u0000[\\\u0007"+
		"\u0007\u0000\u0000\\]\u0007\f\u0000\u0000]\u001c\u0001\u0000\u0000\u0000"+
		"^_\u0007\u0004\u0000\u0000_`\u0007\f\u0000\u0000`a\u0007\r\u0000\u0000"+
		"ah\u0007\u0000\u0000\u0000bc\u0007\u000e\u0000\u0000cd\u0007\n\u0000\u0000"+
		"de\u0007\u0005\u0000\u0000ef\u0007\b\u0000\u0000fh\u0007\u0000\u0000\u0000"+
		"g^\u0001\u0000\u0000\u0000gb\u0001\u0000\u0000\u0000h\u001e\u0001\u0000"+
		"\u0000\u0000ij\u0007\u0002\u0000\u0000jk\u0007\r\u0000\u0000kl\u0007\u0005"+
		"\u0000\u0000lm\u0007\u0005\u0000\u0000m \u0001\u0000\u0000\u0000ns\u0005"+
		"\"\u0000\u0000or\u0003/\u0017\u0000pr\b\u000f\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000qp\u0001\u0000\u0000\u0000ru\u0001\u0000\u0000\u0000sq\u0001"+
		"\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tv\u0001\u0000\u0000\u0000"+
		"us\u0001\u0000\u0000\u0000vw\u0005\"\u0000\u0000w\"\u0001\u0000\u0000"+
		"\u0000xz\u0005-\u0000\u0000yx\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000"+
		"\u0000z{\u0001\u0000\u0000\u0000{|\u0003+\u0015\u0000|~\u0005.\u0000\u0000"+
		"}\u007f\u0007\u0010\u0000\u0000~}\u0001\u0000\u0000\u0000\u007f\u0080"+
		"\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0080\u0081\u0001"+
		"\u0000\u0000\u0000\u0081\u0083\u0001\u0000\u0000\u0000\u0082\u0084\u0003"+
		"-\u0016\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0083\u0084\u0001\u0000"+
		"\u0000\u0000\u0084$\u0001\u0000\u0000\u0000\u0085\u0087\u0005-\u0000\u0000"+
		"\u0086\u0085\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000\u0000"+
		"\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0089\u0003+\u0015\u0000\u0089"+
		"\u008a\u0003-\u0016\u0000\u008a\u0090\u0001\u0000\u0000\u0000\u008b\u008d"+
		"\u0005-\u0000\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008c\u008d\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u0090\u0003"+
		"+\u0015\u0000\u008f\u0086\u0001\u0000\u0000\u0000\u008f\u008c\u0001\u0000"+
		"\u0000\u0000\u0090&\u0001\u0000\u0000\u0000\u0091\u0093\u0007\u0011\u0000"+
		"\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000"+
		"\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095\u0001\u0000\u0000"+
		"\u0000\u0095(\u0001\u0000\u0000\u0000\u0096\u0097\u0005 \u0000\u0000\u0097"+
		"*\u0001\u0000\u0000\u0000\u0098\u00a1\u00050\u0000\u0000\u0099\u009d\u0007"+
		"\u0012\u0000\u0000\u009a\u009c\u0007\u0010\u0000\u0000\u009b\u009a\u0001"+
		"\u0000\u0000\u0000\u009c\u009f\u0001\u0000\u0000\u0000\u009d\u009b\u0001"+
		"\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u00a1\u0001"+
		"\u0000\u0000\u0000\u009f\u009d\u0001\u0000\u0000\u0000\u00a0\u0098\u0001"+
		"\u0000\u0000\u0000\u00a0\u0099\u0001\u0000\u0000\u0000\u00a1,\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a4\u0007\u0000\u0000\u0000\u00a3\u00a5\u0007\u0013"+
		"\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00a7\u0003+\u0015"+
		"\u0000\u00a7.\u0001\u0000\u0000\u0000\u00a8\u00ab\u0005\\\u0000\u0000"+
		"\u00a9\u00ac\u0007\u0014\u0000\u0000\u00aa\u00ac\u00031\u0018\u0000\u00ab"+
		"\u00a9\u0001\u0000\u0000\u0000\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ac"+
		"0\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005u\u0000\u0000\u00ae\u00af\u0003"+
		"3\u0019\u0000\u00af\u00b0\u00033\u0019\u0000\u00b0\u00b1\u00033\u0019"+
		"\u0000\u00b1\u00b2\u00033\u0019\u0000\u00b22\u0001\u0000\u0000\u0000\u00b3"+
		"\u00b4\u0007\u0015\u0000\u0000\u00b44\u0001\u0000\u0000\u0000\u000f\u0000"+
		"gqsy\u0080\u0083\u0086\u008c\u008f\u0094\u009d\u00a0\u00a4\u00ab\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}