package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "cohortss")
@AllArgsConstructor
@NoArgsConstructor
public class CohortEntity {

    @Id
    @UuidGenerator
    private String id;

    private String cohortName;

    private  String description;

    private String dateCohortEnded;

    private String cohortAvatar;

    private String scheduleAnEvent;

    private String announcement;

    private String shareResource;

    private ZonedDateTime dateCohortStarted;

    private String createdBy;

}

//private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//
//public KarraboException(String message, HttpStatus httpStatus) {
//    super(message);
//    this.status = httpStatus;
//}
//
//public KarraboException(String message) {
//    super(message);
//}
//
//public KarraboException(String message, Throwable cause) {
//    super(message, cause);
//}
//
//public KarraboException(Throwable cause) {
//    super(cause);
//}