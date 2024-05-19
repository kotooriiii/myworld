package com.github.kotooriiii.myworld.util.audit;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ResourceBundle;

public enum ChangeLogEventType
{
    PROJECT_NAME_CHANGE__NAME_DETAILS(String.class);

    private Class<?> clazz;

    private static final ResourceBundle changeLogMessages = ResourceBundle.getBundle("ChangeLogMessages");

    <T> ChangeLogEventType(Class<T> stringClass)
    {
        this.clazz = stringClass;
    }

    public String getChangeLogMessage(String oldValue, String newValue)
    {
        JsonNode root = AuditLogUtils.getRootJsonNode(oldValue, newValue);

        String changeLogMessageTemplate = changeLogMessages.getString(this.name());

        return AuditLogUtils.substitute(changeLogMessageTemplate, root);
    }

}
