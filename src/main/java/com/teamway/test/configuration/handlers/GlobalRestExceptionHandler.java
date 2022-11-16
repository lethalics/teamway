package com.teamway.test.configuration.handlers;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ApiError apiError = ApiError.builder()
            .message("Found " + ex.getBindingResult().getErrorCount() + " errors")
            .status(HttpStatus.BAD_REQUEST)
            .errors(addBindingExceptions(ex.getBindingResult()))
            .build();
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
        BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ApiError apiError = ApiError.builder()
            .message("Found " + ex.getBindingResult().getErrorCount() + " errors")
            .status(HttpStatus.BAD_REQUEST)
            .errors(addBindingExceptions(ex))
            .build();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError defaultExceptionHandler(Exception ex) {
        return logErrorAndGetApiErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered an error! Please check the logs for more details.");
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.status());
    }

    private List<ApiSubError> addBindingExceptions(BindingResult bindingResult) {
        final List<ApiSubError> subErrors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> subErrors.add(buildFieldSubErrors(fieldError)));
        bindingResult.getGlobalErrors().forEach(globalError -> subErrors.add(buildGlobalSubErrors(globalError)));

        return subErrors;
    }

    private ApiSubError buildFieldSubErrors(FieldError fieldError) {
        final ApiSubError.ApiSubErrorBuilder subErrorBuilder = ApiSubError.builder();
        subErrorBuilder.field(fieldError.getField());
        subErrorBuilder.rejectedValue(fieldError.getRejectedValue());
        subErrorBuilder.message(fieldError.getDefaultMessage());

        return subErrorBuilder.build();

    }

    private ApiSubError buildGlobalSubErrors(ObjectError globalError) {
        final ApiSubError.ApiSubErrorBuilder subErrorBuilder = ApiSubError.builder();
        subErrorBuilder.message(globalError.getDefaultMessage());

        return subErrorBuilder.build();
    }

    private ApiError logWarnAndGetApiErrorResponse(Exception ex, HttpStatus httpStatus, String message) {
        log.warn("Found exception", ex);
        return getApiErrorResponse(ex, httpStatus, message);
    }

    private ApiError logErrorAndGetApiErrorResponse(Exception ex, HttpStatus httpStatus, String message) {
        log.error("Found exception", ex);
        return getApiErrorResponse(ex, httpStatus, message);
    }

    private ApiError getApiErrorResponse(Exception ex, HttpStatus httpStatus, String message) {
        ApiSubError apiSubError = ApiSubError.builder()
            .message(message)
            .build();

        return ApiError.builder()
            .message(message)
            .status(httpStatus)
            .errors(List.of(apiSubError))
            .build();
    }


}
