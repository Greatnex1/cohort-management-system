package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.mapper;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.request.CohortRequest;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.respond.CohortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CohortRestMapper {
    @Mapping(source = "userId", target = "createdBy")
    Cohort toCohort (CohortRequest cohortRequest, String userId);
  //  @Mapping(source = "cohort.id", target = "id")
  CohortResponse toCohortResponse (Cohort cohort);
}
