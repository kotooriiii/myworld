package com.github.kotooriiii.myworld.util.antlr;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class QueryErrorListener extends BaseErrorListener {
    private List<String> errorMessages;

    public QueryErrorListener() { }

    public boolean errorsFound() { return (errorMessages != null && errorMessages.size() > 0); }

    public List<String> errorMessages() {
        if (errorMessages == null) {
            return Collections.emptyList();
        }
        return errorMessages;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        if (errorMessages == null) {
            errorMessages = new ArrayList<String>();
        }

        final String errorMessage = "line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg;
        errorMessages.add(errorMessage);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) { }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) { }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) { }
}

