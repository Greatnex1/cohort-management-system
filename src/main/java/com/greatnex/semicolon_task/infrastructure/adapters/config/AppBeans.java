package com.greatnex.semicolon_task.infrastructure.adapters.config;

import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.domain.service.CohortService;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.CohortRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {

    @Bean
    public CohortService cohortService(CohortOutputPort cohortOutputPort) {
        return new CohortService(cohortOutputPort);
    }
}
