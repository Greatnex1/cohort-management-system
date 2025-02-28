package com.greatnex.semicolon_task.application.ports.output;

import com.greatnex.semicolon_task.domain.models.AuditLogObject;

public interface AuditLogOutputPort {
    AuditLogObject saveAuditLog(AuditLogObject auditLog);
}
