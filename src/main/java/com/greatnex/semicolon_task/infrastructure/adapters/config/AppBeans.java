package com.greatnex.semicolon_task.infrastructure.adapters.config;

import com.greatnex.semicolon_task.application.ports.output.AuditLogOutputPort;
import com.greatnex.semicolon_task.application.ports.output.CohortOutputPort;
import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.application.ports.output.PlatformUserOutputPort;
import com.greatnex.semicolon_task.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {

    @Bean
    public CohortService cohortService(CohortOutputPort cohortOutputPort, AuditLogOutputPort auditLogOutputPort) {
        return new CohortService(cohortOutputPort, auditLogOutputPort);
    }

    @Bean
    public UserService userService(GetUserFullNameOutputPort getUserNameOutputPort){
        return new UserService(getUserNameOutputPort);
    }
}
