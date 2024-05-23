package com.github.kotooriiii.myworld.service.mapper;

import com.github.kotooriiii.myworld.dto.AuditLogDTO;
import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.model.AuditLog;
import com.github.kotooriiii.myworld.model.Author;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuditLogMapper implements Function<AuditLog, AuditLogDTO> {
    @Override
    public AuditLogDTO apply(AuditLog auditLog) {
        return new AuditLogDTO(
                auditLog.getId(),
                auditLog.getTimestamp(),
                auditLog.getEventType(),
                auditLog.getSourceClass(),
                auditLog.getSourceId(),
                auditLog.getTargetClass(),
                auditLog.getTargetId()
        );
    }
}
