package com.github.kotooriiii.myworld.service;


import com.github.kotooriiii.myworld.dao.AuditLogDao;
import com.github.kotooriiii.myworld.dto.AuditLogDTO;
import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.audit_log.AuditLogEntryRequest;
import com.github.kotooriiii.myworld.dto.request.audit_log.AuditLogUpdateRequest;
import com.github.kotooriiii.myworld.exception.RequestValidationException;
import com.github.kotooriiii.myworld.exception.ResourceNotFoundException;
import com.github.kotooriiii.myworld.model.AuditLog;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.service.mapper.AuditLogMapper;
import com.github.kotooriiii.myworld.util.audit.AuditLogEventType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuditLogService
{

    private final AuditLogDao auditLogDao;
    private final AuditLogMapper auditLogMapper;

    private final AuthorService authorService;

    public AuditLogService(@Qualifier("jdbc") AuditLogDao auditLogDao,
                           AuditLogMapper auditLogMapper,
                           AuthorService authorService)
    {
        this.auditLogDao = auditLogDao;
        this.auditLogMapper = auditLogMapper;
        this.authorService = authorService;
    }

    public Optional<AuditLogDTO> createAuditLogEntry(AuditLogEntryRequest auditLogEntryRequest)
    {
        AuditLog auditLog = AuditLog.builder()
                .eventType(auditLogEntryRequest.eventType())
                .sourceClass(auditLogEntryRequest.sourceClass())
                .sourceId(auditLogEntryRequest.sourceId())
                .targetClass(auditLogEntryRequest.targetClass())
                .targetId(auditLogEntryRequest.targetId())
                .build();

        return auditLogDao.createAuditLogEntry(auditLog).map(auditLogMapper);
    }

    public List<AuditLogDTO> getAllAuditLogs()
    {
        List<AuditLog> auditLogs = auditLogDao.selectAllAuditLogs();

        constructAuditLogAndChangeLogMessage(auditLogs);

        return auditLogs
                .stream()
                .map(auditLogMapper)
                .collect(Collectors.toList());
    }



    public Page<AuditLogDTO> getAuditLogsByPage(Pageable pageable)
    {
        Page<AuditLog> page = auditLogDao.selectAuditLogByPage(pageable);

        constructAuditLogAndChangeLogMessage(page.getContent());


        return page.map(auditLogMapper);
    }

    public AuditLogDTO getAuditLogById(UUID auditLogId)
    {
        Optional<AuditLog> auditLog = auditLogDao.selectAuditLogById(auditLogId);

        auditLog.ifPresent(this::constructAuditLogAndChangeLogMessage);

        return  auditLog
                .map(auditLogMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "audit log entry with id [%s] not found".formatted(auditLogId)
                ));
    }

    public List<AuditLogDTO> getAuditLogsByAuthorId(UUID authorId)
    {
        List<AuditLog> auditLogs = auditLogDao.selectAuditLogsByAuthorId(authorId);

        constructAuditLogAndChangeLogMessage(auditLogs);

        return  auditLogs
                .stream()
                .map(auditLogMapper)
                .collect(Collectors.toList());
    }



    public void updateAuditLog(UUID auditLogId,
                             AuditLogUpdateRequest auditLogUpdateRequest)
    {
        // TODO: for JPA use .getReferenceById(authorId) as it does does not bring object into memory and instead a reference
        AuditLog auditLog = auditLogDao.selectAuditLogById(auditLogId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "audit log entry with id [%s] not found".formatted(auditLogId)
                ));

        boolean changes = false;

        if (auditLogUpdateRequest.eventType() != null && !auditLogUpdateRequest.eventType().equals(auditLog.getEventType()))
        {
            auditLog.setEventType(auditLogUpdateRequest.eventType());
            changes = true;
        }

        if (auditLogUpdateRequest.sourceClass() != null && !auditLogUpdateRequest.sourceClass().equals(auditLog.getSourceClass()))
        {
            auditLog.setSourceClass(auditLogUpdateRequest.sourceClass());
            changes = true;
        }

        if (auditLogUpdateRequest.sourceId() != null && !auditLogUpdateRequest.sourceId().equals(auditLog.getSourceId()))
        {
            auditLog.setSourceId(auditLogUpdateRequest.sourceId());
            changes = true;
        }

        if (auditLogUpdateRequest.targetClass() != null && !auditLogUpdateRequest.targetClass().equals(auditLog.getTargetClass()))
        {
            auditLog.setTargetClass(auditLogUpdateRequest.targetClass());
            changes = true;
        }

        if (auditLogUpdateRequest.targetId() != null && !auditLogUpdateRequest.targetId().equals(auditLog.getTargetId()))
        {
            auditLog.setTargetId(auditLogUpdateRequest.targetId());
            changes = true;
        }


        if (!changes)
        {
            throw new RequestValidationException("no data changes found");
        }

        auditLogDao.updateAuditLog(auditLog);
    }


    public void deleteAuditLogById(UUID auditLogId)
    {
        checkIfAuditLogExistsOrThrow(auditLogId);
        auditLogDao.deleteAuditLogById(auditLogId);
    }

    private void checkIfAuditLogExistsOrThrow(UUID auditLogId)
    {
        if (!auditLogDao.existsAuditLogById(auditLogId))
        {
            throw new ResourceNotFoundException(
                    "audit log entry with id [%s] not found".formatted(auditLogId)
            );
        }
    }

    //
    //
    // HELPER METHODS
    //
    //

    private void constructAuditLogAndChangeLogMessage(AuditLog auditLog)
    {
        constructAuditLogAndChangeLogMessage(List.of(auditLog));
    }
    private void constructAuditLogAndChangeLogMessage(Collection<AuditLog> auditLogs)
    {
        //set change log message
        auditLogs.forEach(auditLog -> auditLog.getChangeLogList().forEach(changeLog -> changeLog.setMessage(changeLog.getEventType().getChangeLogMessage(changeLog.getOldData(), changeLog.getNewData()))));

        //set audit log message
        Map<String, List<UUID>> entityUUIDMap = auditLogs.stream()
                .flatMap(log -> Stream.of(
                        Map.entry(log.getSourceClass(), log.getSourceId()),
                        Map.entry(log.getTargetClass(), log.getTargetId())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        Map<UUID, Object> entityMap = new HashMap<>(); //todo parent class to take care of all sources, and targets

        for (Map.Entry<String, List<UUID>> entry : entityUUIDMap.entrySet()) {
            String className = entry.getKey();
            List<UUID> sourceIds = entry.getValue();

            if(className.equals(Author.class.getSimpleName()))
            {
                Map<UUID, AuthorDTO> allAuthorsByIds = authorService.getAllAuthorsByIds(sourceIds);
                entityMap.putAll(allAuthorsByIds);

            }
            else if(false)
            {
                //todo
            }
        }

        auditLogs.forEach(auditLog ->
                auditLog.setMessage(auditLog.getEventType().getAuditLogMessage(entityMap.get(auditLog.getSourceId()), entityMap.get(auditLog.getTargetId()), auditLog.getChangeLogList()))
        );
    }

}

