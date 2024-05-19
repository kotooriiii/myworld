package com.github.kotooriiii.myworld.dto.request.audit_log;

import com.github.kotooriiii.myworld.util.audit.AuditLogEventType;

import java.util.UUID;

public record AuditLogEntryRequest
        (AuditLogEventType eventType, String sourceClass, UUID sourceId, String targetClass, UUID targetId)
{
}
