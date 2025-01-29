package com.greatnex.semicolon_task.domain.models;

import com.greatnex.semicolon_task.domain.validator.InputValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.greatnex.semicolon_task.domain.validator.InputValidator.validateInput;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cohort {
    private String id;

    private String name;

    private  String description;

    private String schedule;

    private String announcement;

    private String sharedResource;

    private String avatar;

    private String dateEnded;

    private ZonedDateTime dateCreated;

    private String createdBy;


    public  void validateCohortData(){
        validateInput(getName());
        validateInput(getAvatar());
        validateInput(getDescription());
        validateInput(getAnnouncement());
        validateInput(getSharedResource());
        validateInput(getSchedule());
        validateInput(getDateEnded());
    }

//    @ElementCollection(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @NotEmpty
//    Set<String> listOfInstructors = new HashSet<>();
//
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    Set<String> listOfLearners = new HashSet<>();
//
//   @OneToMany
//   private List<Program> programList = new ArrayList<>();
//
//
//    @OneToMany
//     private List<Course> courseList = new ArrayList<>();

//    @OneToMany
//    private List<Learner> learnerList = new ArrayList<>();


//    @ElementCollection(fetch = FetchType.LAZY)
//    @ToString.Exclude
    //private Set<CohortInvitation> cohortInvitations = new HashSet<>();


}
