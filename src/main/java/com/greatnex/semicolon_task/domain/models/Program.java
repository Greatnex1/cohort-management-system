package com.greatnex.semicolon_task.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Program {

    private String id;

    private String nameOfProgram;

    private String programDetails;

    private String dateCreated;

    private String createdBy;

    private int views;

}
