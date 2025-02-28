package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence;

import com.greatnex.semicolon_task.application.ports.output.AuditLogOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.AuditLogObject;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.AuditLogEntity;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper.AuditLogMapper;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLogPersistenceAdapter implements AuditLogOutputPort {

    private final AuditLogMapper auditLogMapper;
    private final AuditLogRepository auditLogRepository;

    @Override
    public AuditLogObject saveAuditLog(AuditLogObject auditLog) {

        if(auditLog == null) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT_ERROR);
        }
        auditLog.validateAuditObject();
        AuditLogEntity auditLogEntity = auditLogMapper.toAuditLogEntity(auditLog);
       auditLogRepository.save(auditLogEntity);
       log.info("Audit log saved ===>> {}", auditLogEntity.getAction());
        return auditLogMapper.toAuditLogObject(auditLogEntity);
    }
}
