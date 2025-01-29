package com.greatnex.semicolon_task.infrastructure.adapters;

import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.exception.CohortException;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.CohortEntity;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper.CohortMapper;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.CohortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CohortPersistenceAdapter implements CohortOutputPort {

    private final CohortRepository cohortRepository;
    private final CohortMapper cohortMapper;

    @Override
    public Cohort saveCohortDetails(Cohort cohort) {
        CohortEntity cohortEntity = cohortMapper.toCohortEntity(cohort);
        CohortEntity savedCohortEntity = cohortRepository.save(cohortEntity);
        return cohortMapper.toCohort(savedCohortEntity);
    }

    @Override
    public Cohort findCohortById(String cohortId) throws CohortException {
        if(StringUtils.isNotEmpty(cohortId)){
            CohortEntity savedCohortEntity = cohortRepository.findById(cohortId).orElseThrow(()->
                    new CohortException(ErrorMessages.COHORT_NOT_FOUND, HttpStatus.NOT_FOUND));
            return cohortMapper.toCohort(savedCohortEntity);
        }

   throw new CohortException(ErrorMessages.COHORT_CANT_BE_NULL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public Page<Cohort> findAllCohort(Pageable pageable) {
        Page<CohortEntity> cohortEntities = cohortRepository.findAll(pageable);

        return getCohortObject(pageable, cohortEntities);
    }

    private Page<Cohort> getCohortObject(Pageable pageable, Page<CohortEntity> cohortEntities) {
        List<Cohort> cohorts = cohortEntities
                .getContent()
                .stream()
                .map(cohortMapper::toCohort)
                .toList();
        return new PageImpl<>(cohorts, pageable, cohortEntities.getTotalElements());
    }

    @Override
    public void deleteCohort(String id) throws CohortException {
     log.info("Deleting cohort by id {}", id);
        if(StringUtils.isNotEmpty(id)){
            cohortRepository.deleteById(id);
        }

        throw new CohortException(ErrorMessages.COHORT_CANT_BE_NULL, HttpStatus.BAD_REQUEST);
    }
}
