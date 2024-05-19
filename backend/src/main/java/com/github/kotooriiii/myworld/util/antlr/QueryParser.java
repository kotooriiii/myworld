package com.github.kotooriiii.myworld.util.antlr;

import com.github.kotooriiii.myworld.util.antlr.exception.QueryParseException;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.grammar.FilterQueryLexer;
import com.github.kotooriiii.myworld.util.antlr.grammar.FilterQueryParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;


public class QueryParser {
    public QueryParser() { }

    public QueryExpression parse(String input) throws QueryParseException
    {
        QueryExpression expression = null;

        if (input != null && (input = input.trim()).length() > 0) {
            final CharStream stream = CharStreams.fromString(input);
            final FilterQueryLexer lexer = new FilterQueryLexer(stream);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final QueryErrorListener errorListener = new QueryErrorListener();
            final QueryParserListener listener = new QueryParserListener();

            final FilterQueryParser parser = new FilterQueryParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            final FilterQueryParser.QueryContext queryContext = parser.query();

            final ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, queryContext);

            List<String> errorMessages = null;
            if (errorListener.errorsFound() && (errorMessages = errorListener.errorMessages()) != null && errorMessages.size() > 0) {
                final StringBuilder builder = new StringBuilder();
                for (String message : errorMessages) {
                    builder.append(message).append(System.lineSeparator());
                }
                builder.setLength(builder.length() - System.lineSeparator().length());

                throw new QueryParseException(builder.toString());
            }

            expression = listener.getExpression();
        }

        return expression;
    }
}
