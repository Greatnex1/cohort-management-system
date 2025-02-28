package com.greatnex.semicolon_task.domain.models;

import jakarta.persistence.Column;
import lombok.*;

import java.time.ZonedDateTime;

import static com.greatnex.semicolon_task.domain.validator.InputValidator.validateInput;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogObject {
    private String id;
    private String action;
    @Column(length = 2800)
    private String description;
    private String keycloakClientId;
    private String actorName;
    private ZonedDateTime dateCreated;
    private String createdBy;
    private String referenceId;

    public void validateAuditObject() {
        validateInput(getDescription(),"description");
        validateInput(getKeycloakClientId(),"keycloakClientId");
        validateInput(getActorName(),"actorName");
    }
}
