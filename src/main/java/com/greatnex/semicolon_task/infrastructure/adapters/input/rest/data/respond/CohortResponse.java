package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.respond;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CohortResponse {
    private String id;

    private String name;

    private  String description;

    private String schedule;

    private String announcement;

    private String sharedResource;

    private String avatar;

    private LocalDate dateEnded;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXXXX'['VV']'")
    private ZonedDateTime dateCreated;

    private String createdBy;

}
