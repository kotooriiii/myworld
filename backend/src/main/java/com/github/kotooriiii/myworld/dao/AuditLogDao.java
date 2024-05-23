package com.github.kotooriiii.myworld.dao;

import com.github.kotooriiii.myworld.model.AuditLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditLogDao
{
    Optional<AuditLog> createAuditLogEntry(AuditLog auditLogToAdd);
    List<AuditLog> selectAllAuditLogs();
    Page<AuditLog> selectAuditLogByPage(Pageable pageable);
    Optional<AuditLog> selectAuditLogById(UUID auditLogId);
    List<AuditLog> selectAuditLogsByAuthorId(UUID authorId);
    boolean existsAuditLogById(UUID auditLogId);
    void updateAuditLog(AuditLog auditLogToUpdate);
    void deleteAuditLogById(UUID auditLogId);
}
