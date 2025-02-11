package com.greatnex.semicolon_task.infrastructure.adapters.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WhiteList {

    static final String[] getPatterns ={
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/documentation/**",
            "/documentation/v3/api-docs/swagger-config",
            "/documentation/v3/api-docs/swagger-config/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/documentation/v3/api-docs",
            "/actuator/**",
            "/reports",
            "/report",
            "/reports/**",
            "/report/**"
    };

}
