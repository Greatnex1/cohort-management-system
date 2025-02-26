package com.greatnex.semicolon_task.infrastructure.adapters.web;

import com.greatnex.semicolon_task.application.ports.input.CohortUseCase;
import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.domain.exception.CohortException;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.constant.UrlConstants;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.ApiResponse;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.mapper.CohortRestMapper;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.request.CohortRequest;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.respond.CohortResponse;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper.CohortMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstants.SCHOOL_URL + "/cohorts")
@RequiredArgsConstructor
@Slf4j
public class CohortController {

    private final CohortUseCase cohortUseCase;
    private final CohortRestMapper cohortRestMapper;
    private final GetUserFullNameOutputPort userFullNameOutputPort;
    

    @PostMapping
    public ResponseEntity<ApiResponse<CohortResponse>> createCohort(PlatformUser identity , @Valid @RequestBody CohortRequest cohortRequest) throws CohortException {
    Cohort cohort = cohortRestMapper.toCohort(cohortRequest, identity.getId());
    cohort = cohortUseCase.createCohort(identity, cohort);
        String userFullName = userFullNameOutputPort.getUserFullName(cohort.getCreatedBy());
        cohort.setCreatedBy(userFullName);
   CohortResponse cohortResponse = cohortRestMapper.toCohortResponse(cohort);

   String message = "Cohort created successfully";
   ApiResponse<CohortResponse> response = ApiResponse.<CohortResponse>builder().build();
    return new ResponseEntity<>(response.created(cohortResponse, message),HttpStatus.CREATED);
    }

@GetMapping
    public ResponseEntity<ApiResponse<Page<CohortResponse>>> getAllCohorts(@RequestParam int page, @RequestParam int size) {
        return null;
}
}