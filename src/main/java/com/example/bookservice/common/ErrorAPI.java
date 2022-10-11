package com.example.bookservice.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Data
@NoArgsConstructor
public class ErrorAPI extends ResponseEntityExceptionHandler {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Common.DATE_TIME_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ErrorAPI> handleAuthenticationException(Exception ex) {

        ErrorAPI re = new ErrorAPI(HttpStatus.UNAUTHORIZED,
                "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ex.getBindingResult().getGlobalErrors().forEach((ObjectError error) -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });

        ErrorAPI apiError = new ErrorAPI(status, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
}

