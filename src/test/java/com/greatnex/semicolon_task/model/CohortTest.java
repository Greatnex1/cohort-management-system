package com.greatnex.semicolon_task.model;

import com.greatnex.semicolon_task.domain.models.Cohort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CohortTest {

     Cohort cohort;


     @BeforeEach
    void setUp() {
         cohort = Cohort.builder()
                 .name("Phoenix")
                 .description("Phoenix Cohort: The cohort of great minds")
                 .announcement("A bright beautiful moment of lifestyle")
                 .sharedResource("IMG003-5678J1")
                 .avatar("Img")
                 .schedule("Classes held daily")
                 .dateCreated(ZonedDateTime.now())
                 .dateEnded(LocalDate.now())
                 .createdBy("Admin")
                 .build();
     }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "undefined"})
    void testExceptionThrownWhenCapacityFieldIsInvalid(String name) {
        cohort.setName(name);
        assertThrows(IllegalArgumentException.class, () -> cohort.validateCohortData());
    }
}

