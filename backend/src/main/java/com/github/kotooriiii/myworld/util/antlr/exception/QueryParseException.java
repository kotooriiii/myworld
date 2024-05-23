package com.github.kotooriiii.myworld.util.antlr.exception;

public class QueryParseException extends Exception {
    private static final long serialVersionUID = 1L;

    public QueryParseException() {
        super();
    }

    public QueryParseException(String message) {
        super(message);
    }

    public QueryParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryParseException(Throwable cause) {
        super(cause);
    }
}
