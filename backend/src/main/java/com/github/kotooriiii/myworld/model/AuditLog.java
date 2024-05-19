package com.github.kotooriiii.myworld.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.kotooriiii.myworld.util.audit.AuditLogEventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditLogEventType eventType;

    @Column(nullable = false)
    private String sourceClass;

    @Column(nullable = false)
    private UUID sourceId;

    @Column(nullable = false)
    private String targetClass;

    @Column(nullable = false)
    private UUID targetId;

    @OneToMany(mappedBy = "auditLog", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChangeLog> changeLogList;

    @Transient
    private String message;


}