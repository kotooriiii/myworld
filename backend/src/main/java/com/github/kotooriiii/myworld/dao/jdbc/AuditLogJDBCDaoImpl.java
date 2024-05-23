package com.github.kotooriiii.myworld.dao.jdbc;

import com.github.kotooriiii.myworld.dao.AuditLogDao;
import com.github.kotooriiii.myworld.model.AuditLog;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.dao.jdbc.extractor.AuditLogResultSetExtractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
@Qualifier("jdbc")
public class AuditLogJDBCDaoImpl implements AuditLogDao
{
    private static final String SQL_CREATE_AUDIT_LOG_ENTRY = """
            INSERT INTO audit_logs(id, timestamp, event_type, source_class, source_id, target_class, target_id)
                VALUES (?, ?, ?, ?, ?, ?, ?);
            """; //todo with all sql

    private final JdbcClient jdbcClient;

    public AuditLogJDBCDaoImpl(JdbcClient jdbcClient)
    {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<AuditLog> createAuditLogEntry(AuditLog auditLogToAdd)
    {

        UUID uuid = UUID.randomUUID(); //todo do id in service
        int result = jdbcClient.sql(SQL_CREATE_AUDIT_LOG_ENTRY)
                .params(Stream.of(uuid, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)), auditLogToAdd.getEventType().name(), auditLogToAdd.getSourceClass(), auditLogToAdd.getSourceId(), auditLogToAdd.getTargetClass(), auditLogToAdd.getTargetId()).toList())
                .update();
        Assert.state(result == 1, "Failed to create audit log entry with event type \"" + auditLogToAdd.getEventType().name() + "\" performed by user ID \"" + auditLogToAdd.getSourceId() + "\".");
        return selectAuditLogById(uuid);
    }

    @Override
    public List<AuditLog> selectAllAuditLogs()
    {
        var sql = """
                SELECT a.*, c.*
                FROM audit_logs a
                LEFT JOIN change_logs c ON a.id = c.audit_log_id
                LIMIT 1000;
                """; //todo 1000 magic number?

        return jdbcClient.sql(sql).query(new AuditLogResultSetExtractor());
    }

    @Override
    public Page<AuditLog> selectAuditLogByPage(Pageable pageable)
    {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        var sql = """
                SELECT a.*, c.*
                FROM audit_logs a
                LEFT JOIN change_logs c ON a.id = c.audit_log_id
                LIMIT :limit
                OFFSET :offset;
                """;

        List<AuditLog> auditLogs = jdbcClient.sql(sql)
                .param("limit", pageSize)
                .param("offset", offset)
                .query(new AuditLogResultSetExtractor());

        var countSql = """
                SELECT COUNT(*)
                FROM audit_logs;
                """;
        int totalCount = jdbcClient.sql(countSql)
                .query(Integer.class)
                .single();

        return new PageImpl<AuditLog>(auditLogs, pageable, totalCount);
    }

    @Override
    public Optional<AuditLog> selectAuditLogById(UUID auditLogId)
    {
        var sql = """
                SELECT a.*, c.*
                FROM audit_logs a
                LEFT JOIN change_logs c ON a.id = c.audit_log_id
                WHERE id = :id;
                """;
        return jdbcClient.sql(sql)
                .param("id", auditLogId)
                .query(new AuditLogResultSetExtractor())
                .stream().findFirst();
    }

    @Override
    public List<AuditLog> selectAuditLogsByAuthorId(UUID authorId)
    {
        var sql = """
                SELECT a.*, c.*
                FROM audit_logs a
                LEFT JOIN change_logs c ON a.id = c.audit_log_id
                WHERE source_class = :source_class AND source_id = :source_id;
                """;
        return jdbcClient.sql(sql)
                .param("source_class", Author.class.getSimpleName()) //todo is this ok
                .param("source_id", authorId)
                .query(new AuditLogResultSetExtractor());
    }

    @Override
    public boolean existsAuditLogById(UUID auditLogId)
    {
        var sql = """
                SELECT count(id)
                FROM audit_logs
                WHERE id = :id
                """;
        int count = jdbcClient.sql(sql)
                .param("id", auditLogId)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    @Override
    public void updateAuditLog(AuditLog auditLogToUpdate)
    {
        var sql = """
                UPDATE audit_logs
                SET timestamp = ?, event_type = ?, source_class = ?, source_id = ?, target_class = ?, target_id = ?
                WHERE id = :id
                """;
        var updated = jdbcClient.sql(sql)
                .params(Stream.of(auditLogToUpdate.getTimestamp(), auditLogToUpdate.getEventType(), auditLogToUpdate.getSourceClass(), auditLogToUpdate.getSourceId(), auditLogToUpdate.getTargetClass(), auditLogToUpdate.getTargetId()).toList())
                .param("id", auditLogToUpdate.getId())
                .update();

        Assert.state(updated == 1, "Failed to update audit log entry with id : " + auditLogToUpdate.getId());
    }

    @Override
    public void deleteAuditLogById(UUID auditLogId)
    {
        var sql = """
                DELETE
                FROM audit_logs
                WHERE id = :id
                """;
        int result = jdbcClient.sql(sql)
                .param("id", auditLogId)
                .update();

        Assert.state(result == 1, "Failed to delete audit log entry with ID : " + auditLogId);
    }
}
