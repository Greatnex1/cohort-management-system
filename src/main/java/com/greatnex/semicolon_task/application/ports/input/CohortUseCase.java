package com.greatnex.semicolon_task.application.ports.input;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.exception.CohortException;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import org.springframework.data.domain.Page;

public interface CohortUseCase {
    Cohort createCohort(PlatformUser identity, Cohort cohort) throws CohortException;
    Cohort viewCohortDetails(String cohortId) throws CohortException;
    Page<Cohort> viewAllCohorts(int page, int size) ;


}
