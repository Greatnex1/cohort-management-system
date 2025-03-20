package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.respond;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CourseResponse {
    private String id;
    private String courseTitle;
    private int coursePeriod;
    private String courseInformation;
    private String quizzes;
    private String polls;
}
