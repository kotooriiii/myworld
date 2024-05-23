package com.github.kotooriiii.myworld.util.audit;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kotooriiii.myworld.model.ChangeLog;

import java.util.List;
import java.util.ResourceBundle;

public enum AuditLogEventType
{

    //Manage collaborators
    ADD_COLLABORATOR,

    //Project Name Change
    PROJECT_NAME_CHANGE;

    private static final ResourceBundle auditLogMessages = ResourceBundle.getBundle("AuditLogMessages");

    public <T> String getAuditLogMessage(Object source, Object target, List<ChangeLog> changeLogList) //todo someway to transform these objects to a closer parent interface/superclass?
    {
        JsonNode root = AuditLogUtils.getRootJsonNode(source, target, changeLogList);
        String auditLogMessageTemplate = auditLogMessages.getString(this.name());

        return AuditLogUtils.substitute(auditLogMessageTemplate, root);
    }
}
