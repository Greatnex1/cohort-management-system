package com.greatnex.semicolon_task.infrastructure.adapters.input.rest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponse<T> {

    private String message;

    private T body;

    private int statusCode;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXXXX'['VV']'")
    private ZonedDateTime timestamp;
    @JsonProperty("successful")
    private boolean isSuccessful;

    public static ApiResponse<String> ok(String object) {

        return ApiResponse.<String>builder()
                .body(null)
                .message(object)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public static Object ok(Object object) {

        return ApiResponse.builder()
                .body(object)
                .message("")
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public ApiResponse<T> ok(T object, String message) {

        return ApiResponse.<T>builder()
                .body(object)
                .message(message)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public ApiResponse<T> created(T object, String message) {

        return ApiResponse.<T>builder()
                .body(object)
                .message(message)
                .statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> ok(String message, T body) {
        return new ApiResponse<>(
                message,
                body,
                HttpStatus.OK.value(),
                ZonedDateTime.now(),
                true
        );
    }

    public static ApiResponse<String> badRequest(String message) {

        return ApiResponse.<String>builder()
                .body(null)
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public static ApiResponse<String> internalServerError(@Nullable String message) {

        return ApiResponse.<String>builder()
                .body(null)
                .message(message)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .isSuccessful(false)
                .timestamp(ZonedDateTime.now())
                .build();
    }
}