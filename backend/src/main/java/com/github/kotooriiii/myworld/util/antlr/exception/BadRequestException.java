package com.github.kotooriiii.myworld.util.antlr.exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
