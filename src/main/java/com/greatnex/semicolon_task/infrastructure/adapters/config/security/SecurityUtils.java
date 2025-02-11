package com.greatnex.semicolon_task.infrastructure.adapters.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SecurityUtils {

    private final ObjectMapper objectMapper;


    public void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        ApiResponse<Object> apiResponse = ApiResponse.builder().message(message).statusCode(HttpStatus.UNAUTHORIZED.value()).body(null).build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }

}
