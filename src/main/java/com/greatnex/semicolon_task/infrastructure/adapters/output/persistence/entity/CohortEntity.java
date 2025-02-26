package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "cohorts")
@AllArgsConstructor
@NoArgsConstructor
public class CohortEntity {
    @Id
    @UuidGenerator
    private String id;

   @Column(unique = true)
    private String name;

    @Column(length = 5000)
    private  String description;

    private String avatar;

    private String schedule;

    private String announcement;

    private String sharedResource;

    private LocalDate dateEnded;

    private ZonedDateTime dateCreated;

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