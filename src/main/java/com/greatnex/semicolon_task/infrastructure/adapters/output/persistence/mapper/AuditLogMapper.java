package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper;

import com.greatnex.semicolon_task.domain.models.AuditLogObject;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.AuditLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuditLogMapper {
    AuditLogEntity toAuditLogEntity(AuditLogObject auditLogObject);
    AuditLogObject toAuditLogObject(AuditLogEntity auditLogEntity);
}
