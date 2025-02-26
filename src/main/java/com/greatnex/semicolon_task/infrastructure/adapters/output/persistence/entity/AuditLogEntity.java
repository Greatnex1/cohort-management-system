package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_actorName", columnList = "actorName"),
        @Index(name = "idx_keycloakClientId", columnList = "actorName")
})
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogEntity {
    @Id
    @UuidGenerator
    private String id;
    private String action;
    @Column(length = 2800)
    private String description;
    private String actorName;
    private String keycloakClientId;
    private ZonedDateTime dateCreated;
    private String createdBy;
    private String approvedBy;
    private String declinedBy;
    private String referenceId;

}
