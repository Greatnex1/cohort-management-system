package com.greatnex.semicolon_task.infrastructure.adapters;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.exception.CohortException;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.CohortPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@ActiveProfiles("")
class CohortPersistenceAdapterTest {

    @Autowired
    private CohortPersistenceAdapter cohortPersistenceAdapter;

     private Cohort cohort;
    @BeforeEach
    void setUp() {
        cohort = Cohort.builder()
                .name("Phoenix")
                .description("Phoenix Cohort: The cohort of great minds")
                .announcement("A bright beautiful moment of lifestyle")
                .sharedResource("IMG003-5678J1")
                .schedule("Classes held daily")
                .dateCreated(ZonedDateTime.now())
                .dateEnded(LocalDate.now())
                .createdBy("Admin")
                .build();
    }

    @Test
    void saveCohortDetails() throws CohortException {
        Cohort savedCohort = cohortPersistenceAdapter.saveCohortDetails(cohort);
        assertNotNull(savedCohort);
        assertEquals(cohort.getName(), savedCohort.getName());
        cohortPersistenceAdapter.deleteCohort(savedCohort.getId());
    }

    @Test
    void findCohortById() {
        try {
            Cohort savedCohort = cohortPersistenceAdapter.saveCohortDetails(cohort);
            Cohort cohortDetails = cohortPersistenceAdapter.findCohortById(savedCohort.getId());
            assertNotNull(cohortDetails);
            assertEquals(savedCohort.getName(), cohortDetails.getName());
            assertEquals(savedCohort.getDescription(), cohortDetails.getDescription());
            cohortPersistenceAdapter.deleteCohort(savedCohort.getId());
        } catch (CohortException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindCohortDetailsThatDoesNotExist() {
        assertThrows(CohortException.class,()-> cohortPersistenceAdapter.findCohortById("12234"));
    }
    @Test
    void testGetAllCohort() throws CohortException {
      cohort = cohortPersistenceAdapter.saveCohortDetails(cohort);
        Pageable cohortPageable = PageRequest.of(0, 1);
        Page <Cohort> cohortPage = cohortPersistenceAdapter.findAllCohort(cohortPageable);

        assertEquals(cohortPageable.getPageSize(), cohortPage.getNumberOfElements());
        assertNotNull(cohortPage.getContent());
        cohortPersistenceAdapter.deleteCohort(cohort.getId());
    }

    @Test
    void deleteCohort() {
       try {
           cohort = cohortPersistenceAdapter.saveCohortDetails(cohort);
           assertNotNull(cohort);
           cohortPersistenceAdapter.deleteCohort(cohort.getId());
       }catch (CohortException e) {
           throw new RuntimeException(e);
       }
    }
}