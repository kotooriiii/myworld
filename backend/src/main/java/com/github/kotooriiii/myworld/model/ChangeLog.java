package com.github.kotooriiii.myworld.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kotooriiii.myworld.util.audit.ChangeLogEventType;
import lombok.*;

import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "change_logs")
public class ChangeLog implements GenericModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChangeLogEventType eventType;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String oldData;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String newData;

    @ManyToOne
    @JoinColumn(name = "audit_log_id")
    @ToString.Exclude
    @JsonBackReference
    private AuditLog auditLog;

    @Transient
    private String message;
}


