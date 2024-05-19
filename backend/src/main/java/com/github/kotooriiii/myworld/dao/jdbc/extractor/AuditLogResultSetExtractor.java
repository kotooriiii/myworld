package com.github.kotooriiii.myworld.dao.jdbc.extractor;

import com.github.kotooriiii.myworld.model.AuditLog;
import com.github.kotooriiii.myworld.model.ChangeLog;
import com.github.kotooriiii.myworld.util.audit.AuditLogEventType;
import com.github.kotooriiii.myworld.util.audit.ChangeLogEventType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
public class AuditLogResultSetExtractor implements ResultSetExtractor<List<AuditLog>>
{
    @Override
    public List<AuditLog> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<UUID, AuditLog> auditLogEntries = new LinkedHashMap<>();

        while (rs.next()) {
            UUID auditLogId = (UUID) rs.getObject("a.id");

            AuditLog auditLog = auditLogEntries.get(auditLogId);
            if (auditLog == null) {

                auditLog = extractAuditLogEntry(rs, auditLogId);
                auditLogEntries.put(auditLogId, auditLog);
            }


            auditLog.getChangeLogList().add(extractChangeLog(rs, auditLog));
        }
        return new ArrayList<>(auditLogEntries.values());
    }

    private static AuditLog extractAuditLogEntry(ResultSet rs, UUID auditLogId)
            throws SQLException
    {
        Timestamp sqlTimestamp = rs.getTimestamp("a.timestamp");
        String eventTypeString = rs.getString("a.event_type");
        String sourceClassString = rs.getString("a.source_class"); //todo, when do i convert to class, when do i query for said sourceIds?
        UUID sourceId = (UUID) rs.getObject("a.source_id");
        String targetClassString = rs.getString("a.target_class");//todo, when do i convert to class, when do i query for said sourceIds?
        UUID targetId = (UUID) rs.getObject("a.target_id");

        return AuditLog.builder()
                .id(auditLogId)
                .timestamp(sqlTimestamp.toLocalDateTime())
                .eventType(AuditLogEventType.valueOf(eventTypeString))
                .sourceClass(sourceClassString)
                .sourceId(sourceId)
                .targetClass(targetClassString)
                .targetId(targetId)
                .changeLogList(new ArrayList<>())
                .build();
    }

    private ChangeLog extractChangeLog(ResultSet rs, AuditLog auditLog)
            throws SQLException
    {
        UUID changeLogId = (UUID) rs.getObject("c.id");
        Timestamp changeLogTimestamp = rs.getTimestamp("c.timestamp");
        String eventTypeString = rs.getString("c.event_type");
        String oldData = rs.getString("c.old_data");
        String newData = rs.getString("c.new_data");//todo, when do i convert to JSON structure

        return ChangeLog.builder()
                .id(changeLogId)
                .timestamp(changeLogTimestamp.toLocalDateTime())
                .eventType(ChangeLogEventType.valueOf(eventTypeString))
                .oldData(oldData)
                .newData(newData)
                .auditLog(auditLog)
                .build();
    }
}
