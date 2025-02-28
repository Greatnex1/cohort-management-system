package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository;

import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String> {
}
