package com.github.kotooriiii.myworld.controller;

import com.github.kotooriiii.myworld.dto.AuditLogDTO;
import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.audit_log.AuditLogEntryRequest;
import com.github.kotooriiii.myworld.dto.request.audit_log.AuditLogUpdateRequest;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.request.author.AuthorUpdateRequest;
import com.github.kotooriiii.myworld.exception.ResourceNotFoundException;
import com.github.kotooriiii.myworld.model.AuditLog;
import com.github.kotooriiii.myworld.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/audit_log")
public class AuditLogController
{
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService)
    {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<AuditLogDTO> addAuditLogEntry(
            @RequestBody AuditLogEntryRequest request)
    {
        Optional<AuditLogDTO> auditLogEntry = auditLogService.createAuditLogEntry(request);
        return ResponseEntity.ok(auditLogEntry.orElseThrow(() -> new ResourceNotFoundException(
                "audit log entry was not created" //todo
        )));
    }

    @GetMapping("all")
    public List<AuditLogDTO> getAuditLogs()
    {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping
    public Page<AuditLogDTO> getAuditLogsByPage(@PageableDefault(value = 20, page = 0) Pageable pageable)
    {
        return auditLogService.getAuditLogsByPage(pageable);
    }

    @GetMapping("{auditLogId}")
    public AuditLogDTO getAuditLogById(
            @PathVariable("auditLogId") UUID auditLogId)
    {
        return auditLogService.getAuditLogById(auditLogId);
    }

    @GetMapping("{authorId}")
    public List<AuditLogDTO> getAuditLogByAuthorId(
            @PathVariable("authorId") UUID authorId)
    {
        return auditLogService.getAuditLogsByAuthorId(authorId);
    }

    @PutMapping("{auditLogId}")
    public void updateAuditLog(
            @PathVariable("auditLogId") UUID auditLogId,
            @RequestBody AuditLogUpdateRequest updateRequest)
    {
        auditLogService.updateAuditLog(auditLogId, updateRequest);
    }

    @DeleteMapping("{auditLogId}")
    public void deleteAuditLogById(
            @PathVariable("auditLogId") UUID auditLogId)
    {
        auditLogService.deleteAuditLogById(auditLogId);
    }
}
