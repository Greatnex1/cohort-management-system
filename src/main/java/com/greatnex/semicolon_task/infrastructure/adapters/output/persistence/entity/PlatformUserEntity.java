package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlatformUserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private String keycloakClientId;
    private ZonedDateTime dateCreated;
}
