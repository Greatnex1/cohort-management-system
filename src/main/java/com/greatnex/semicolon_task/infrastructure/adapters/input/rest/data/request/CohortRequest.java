package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.request;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CohortRequest {
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "name" )
    private String name;
    @NotEmpty(message = ErrorMessages.FIELD_IS_REQUIRED + "description" )
    private String description;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "announcement" )
    private String announcement;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "avatar" )
    private String avatar;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "schedule" )
    private String schedule;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "sharedResource" )
    private String sharedResource;
    private LocalDate dateEnded;

}
