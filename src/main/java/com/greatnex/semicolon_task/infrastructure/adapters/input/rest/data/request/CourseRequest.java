package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.request;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {
   @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "courseTitle")
    private String courseTitle;
   @Positive
    private int coursePeriod;
   @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "courseInformation")
    private String courseInformation;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "quizzes")
    private String quizzes;
    @NotBlank(message = ErrorMessages.FIELD_IS_REQUIRED + "polls")
    private String polls;
}
