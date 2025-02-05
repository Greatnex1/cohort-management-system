package com.greatnex.semicolon_task.service;

import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.service.CohortService;
import com.greatnex.semicolon_task.exception.CohortException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class CohortServiceTest {

    @MockBean
    private CohortOutputPort cohortOutputPort;

    @Autowired
    private CohortService cohortService;

     Cohort cohort;


     @BeforeEach
    void setUp() {
         cohort = Cohort.builder()
                 .name("Phoenix")
                 .description("Phoenix Cohort: The cohort of great minds")
                 .announcement("A bright beautiful moment of lifestyle")
                 .avatar("Img-3344-34")
                 .sharedResource("IMG003-5678J1")
                 .schedule("Classes held daily")
                 .dateCreated(ZonedDateTime.now())
                 .dateEnded(LocalDate.now())
                 .createdBy("Admin")
                 .build();

     }

     @Test
    void testCreateCohort() throws CohortException {
         when(cohortOutputPort.saveCohortDetails(any(Cohort.class))).thenReturn(cohort);
        log.info("Created Cohort: {}", cohort);
        Cohort savedCohort = cohortService.createCohort(cohort);
        assertNotNull(savedCohort);
        assertEquals(cohort.getName(), savedCohort.getName());

     }

     @Test
    void testEmptyCohortName(){
         cohort = Cohort.builder()
                 .name("")
                 .announcement("Today is a great day")
                 .avatar("Img1123-45")
                 .build();

         CohortException exception = assertThrows(CohortException.class, () -> cohortService.createCohort(cohort));
         assertEquals (exception.getLocalizedMessage(),  exception.getMessage());
     }

     @Test
    void testViewCohortDetails() throws CohortException {
try{
    cohort.setId("343578");
    when(cohortOutputPort.findCohortById(cohort.getId())).thenReturn(cohort);
    Cohort savedCohort = cohortService.viewCohortDetails(cohort.getId());
    assertNotNull(savedCohort);
    assertEquals(cohort.getName(), savedCohort.getName());
    assertEquals(cohort.getId(), savedCohort.getId());
}catch (CohortException e){
   throw new  RuntimeException(e.getMessage());
}
     }

     @Test
    void testViewAllCohort() throws CohortException {

         List<Cohort> cohorts  = new ArrayList<>();
         cohorts.add(cohort);
         Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"dateCreated"));
         Page<Cohort> cohortPage = new PageImpl<>(cohorts, pageable, cohorts.size());
         when(cohortOutputPort.findAllCohort(pageable)).thenReturn(cohortPage);

         Page<Cohort> result = cohortService.viewAllCohorts(0,10);
         assertEquals(1, result.getTotalElements());

         verify(cohortOutputPort, times(1)).findAllCohort(pageable);

     }
}
