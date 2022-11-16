package com.teamway.test.configuration.handlers;

import java.util.List;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApiError(HttpStatus status, String message, List<ApiSubError> errors) {

    public static ApiError singleError(HttpStatus status, String errorMessage) {
        ApiSubError subError = ApiSubError.builder()
            .message(errorMessage)
            .build();
        return ApiError.builder()
            .status(status)
            .message(errorMessage)
            .errors(List.of(subError))
            .build();
    }


}
