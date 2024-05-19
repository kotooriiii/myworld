package com.github.kotooriiii.myworld.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceExistsException extends RuntimeException
{

    private final Object entityInfo;

    public ResourceExistsException(String message, Object entityInfo) {
        super(message);
        this.entityInfo = entityInfo;
    }

    public Object getEntityInfo() {
        return entityInfo;
    }

    @Override
    public String getMessage()
    {
        return getMessage() + ". Resource Info: " + entityInfo;
    }
}