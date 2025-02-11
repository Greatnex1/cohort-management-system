package com.greatnex.semicolon_task.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import static com.greatnex.semicolon_task.domain.validator.InputValidator.validateInput;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Cohort {
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


    public  void validateCohortData() {
        validateInput(getName(), "name");
        validateInput(getAvatar(), "avatar");
        validateInput(getDescription(), "description");
        validateInput(getAnnouncement(), "announcement");
        validateInput(getSharedResource(), "sharedResource");
        validateInput(getSchedule(), "schedule");


    }

        public AuditLogObject buildCohortAuditLog (PlatformUser identity) {
            AuditLogObject auditLogObject = AuditLogObject.builder()
                    .action("CREATE COHORT")
                    .description(String.format("User: %s %s performed action: Cohort creation on %s with with cohort name: %s.",
                            identity.getFirstName(), identity.getLastName(),
                            getDateCreated(), getName()))
                    .createdBy(identity.getId())
                    .actorName(identity.getFirstName().concat(" ").concat(identity.getLastName()))
                    .keycloakClientId(identity.getKeycloakClientId())
                    .dateCreated(getDateCreated())
                    .build();
            log.info("Saving Cohort Audit log =======> {}", auditLogObject.getDescription());
            return auditLogObject;
        }


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

