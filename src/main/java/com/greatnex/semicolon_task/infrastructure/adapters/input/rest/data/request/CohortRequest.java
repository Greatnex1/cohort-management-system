package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.request;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CohortRequest {
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "cohortName" )
    private String cohortName;
    private String cohortDescription;


}
