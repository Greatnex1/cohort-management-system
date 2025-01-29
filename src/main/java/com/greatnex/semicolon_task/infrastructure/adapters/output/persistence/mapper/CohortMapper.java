package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.CohortEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CohortMapper {
    CohortEntity toCohortEntity(Cohort cohort);
    Cohort toCohort(CohortEntity cohortEntity);
}
