package com.greatnex.semicolon_task.application.ports.output;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.exception.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CohortOutputPort {
    Cohort saveCohortDetails(Cohort cohort);
    Cohort findCohortById(String cohortId) throws GenericException;
    Page<Cohort> findAllCohort(Pageable pageable);
    void deleteCohort(String id) throws GenericException;
    boolean checkCohortNameAlreadyExist(String id);
}
