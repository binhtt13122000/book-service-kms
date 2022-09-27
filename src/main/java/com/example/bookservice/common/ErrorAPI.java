package com.example.bookservice.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Data
@NoArgsConstructor
public class ErrorAPI extends ResponseEntityExceptionHandler {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Common.DATE_TIME_FORMAT)
    private LocalDateTime timeStamp;
    private Map<String, String> errors;

    public ErrorAPI(HttpStatus status, Map<String, String> errors) {
        super();
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.errors = errors;
    }

    public ErrorAPI(HttpStatus status, String error) {
        super();
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.errors = new HashMap<>();
        errors.put(ErrorMessages.ERROR_FIELD, error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        ErrorAPI apiError = new ErrorAPI(status, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
}
