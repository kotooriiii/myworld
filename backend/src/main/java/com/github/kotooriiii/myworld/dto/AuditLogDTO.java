package com.github.kotooriiii.myworld.dto;

import com.github.kotooriiii.myworld.util.audit.AuditLogEventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogDTO
        (
    UUID id,
    LocalDateTime timestamp,
    AuditLogEventType eventType,
    String sourceClass,
    UUID sourceId,
    String targetClass,
    UUID targetId)
{

}