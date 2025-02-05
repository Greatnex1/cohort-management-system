package com.greatnex.semicolon_task.domain.service;

import com.greatnex.semicolon_task.application.ports.input.CohortUseCase;
import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.validator.InputValidator;
import com.greatnex.semicolon_task.exception.CohortException;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.CohortRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


import static com.greatnex.semicolon_task.domain.validator.InputValidator.validateInput;

@RequiredArgsConstructor
@Slf4j

public class CohortService implements CohortUseCase {

    private final CohortOutputPort cohortOutputPort;


    @Override
    public Cohort createCohort(Cohort cohort) throws CohortException {
        try{
            if (ObjectUtils.isEmpty(cohort)) {
                throw new IllegalArgumentException(String.format(ErrorMessages.NULL_OBJECT));
            }
            cohort.validateCohortData();
            cohort.setDateCreated(ZonedDateTime.now());
            cohort.setCreatedBy(cohort.getId());

       Cohort cohortObject = cohortOutputPort.saveCohortDetails(cohort);
       log.info("Cohort details created successfully =====> {}", cohortObject.getName());
            log.info("Cohort details created successfully, id =====> {}", cohortObject.getId());

        } catch (IllegalArgumentException e) {
            throw new CohortException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }

        return cohort;
    }

    @Override
    public Cohort viewCohortDetails(String cohortId) throws CohortException {
        validateInput(cohortId , "cohortId");
        return cohortOutputPort.findCohortById(cohortId);
    }

    @Override
    public Page<Cohort> viewAllCohorts(int page, int size) {
    PageRequestData pageInfo = getPageRequestData(page, size);

         Page<Cohort> cohorts;
        Pageable pageable = PageRequest.of(pageInfo.page(), pageInfo.size(), Sort.by(Sort.Direction.DESC, "dateCreated"));
       cohorts  = cohortOutputPort.findAllCohort(pageable);
        return cohorts;
    }


    public static @NotNull PageRequestData getPageRequestData(int page, int size) {
        if(page < 0)
            page = 0;

        if (size < 0)
            size = 10;

        return new PageRequestData(page, size);
    }

    public record PageRequestData(int page, int size) {

    }
}
