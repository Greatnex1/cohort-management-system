package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    @Id
    private String id;
    @Column(unique = true, nullable = false)
    private String courseTitle;
    private int coursePeriod;
    private String courseInformation;
    private String quizzes;
    private String polls;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
