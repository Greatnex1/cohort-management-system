package com.greatnex.semicolon_task.domain.service;

import com.greatnex.semicolon_task.application.ports.input.CohortUseCase;
import com.greatnex.semicolon_task.application.ports.output.AuditLogOutputPort;
import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.AuditLogObject;
import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.exception.CohortException;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
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
    private final AuditLogOutputPort auditLogOutputPort;


    @Override
    public Cohort createCohort(PlatformUser identity, Cohort cohort) throws CohortException {
        try{
            if (ObjectUtils.isEmpty(cohort)) {
                throw new IllegalArgumentException(String.format(ErrorMessages.NULL_OBJECT));
            }
            if(cohortOutputPort.checkCohortNameAlreadyExist(cohort.getName())){
                throw new CohortException(String.format(ErrorMessages.COHORT_NAME_EXISTS));
            }
            cohort.setDateCreated(ZonedDateTime.now());

            if(ObjectUtils.isNotEmpty(identity)) {
                cohort.setCreatedBy(identity.getId());
            }else throw new  IllegalArgumentException(String.format(ErrorMessages.EMPTY_INPUT_ERROR, "created by"));

            cohort.validateCohortData();
            log.info("Cohort info: {}", cohort.getName());
            cohort.setCreatedBy(identity.getId());
       Cohort cohortObject = cohortOutputPort.saveCohortDetails(cohort);
       log.info("Cohort details created successfully =====> {}", cohortObject.getName());
            log.info("Cohort details created successfully, id =====> {}", cohortObject.getId());

            AuditLogObject auditLogObject = cohortObject.buildCohortAuditLog(identity);
            auditLogOutputPort.saveAuditLog(auditLogObject);

            return cohortObject;
        } catch (IllegalArgumentException e) {
            throw new CohortException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }


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
