package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence;

import com.greatnex.semicolon_task.domain.models.AuditLogObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class AuditLogPersistenceAdapterTest {
    @Autowired
    private AuditLogPersistenceAdapter auditLogPersistenceAdapter;
    private AuditLogObject auditLogObject;

    @BeforeEach
    void setUp() {
        auditLogObject = AuditLogObject.builder()
                .action("STOCK")
                .description("Cohort created")
                .createdBy("Admin")
                .dateCreated(ZonedDateTime.now())
                .build();
    }

    @Test
    void saveAuditLog(){
        auditLogObject.setKeycloakClientId("weryet345");
        auditLogObject.setActorName("john");
        AuditLogObject savedAuditLog = auditLogPersistenceAdapter.saveAuditLog(auditLogObject);
        assertNotNull(savedAuditLog.getId());
        assertNotNull(savedAuditLog.getAction());
        assertNotNull(savedAuditLog.getDateCreated());
        assertEquals(auditLogObject.getAction(), savedAuditLog.getAction());
        assertEquals(auditLogObject.getDescription(), savedAuditLog.getDescription());
        assertEquals(auditLogObject.getCreatedBy(), savedAuditLog.getCreatedBy());
    }

    @Test
    void saveAuditLogWithNullInput(){
        assertThrows(IllegalArgumentException.class, () -> auditLogPersistenceAdapter.saveAuditLog(null));
    }


    @Test
    void saveAuditWithNullDescription(){
        auditLogObject.setDescription(null);
        assertThrows(IllegalArgumentException.class, () -> auditLogPersistenceAdapter.saveAuditLog(auditLogObject));
    }

    @Test
    void saveAuditWithEmptyDescription(){
        auditLogObject.setDescription("");
        assertThrows(IllegalArgumentException.class, () -> auditLogPersistenceAdapter.saveAuditLog(auditLogObject));
    }
}