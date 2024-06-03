package com.modak.challenge.CoreNotificationService.configuration;

import com.modak.challenge.CoreNotificationService.configuration.exception.custom.ErrorMessage;
import com.modak.challenge.CoreNotificationService.configuration.exception.custom.ExceedLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ExceedLimitException.class })
    public ResponseEntity<ErrorMessage> handleExceedLimitException(ExceedLimitException ex) {
        log.error("[ErrorHandler:handleExceedLimitException] "+ ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode("CNS-429")
                        .errorDescription(ex.getErrorDescription())
                        .build(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class } )
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request)
    {
        log.error("handleConstrainViolations",ex);
        ErrorMessage error = ErrorMessage.builder().errorCode("CNS-400")
                .errorDescription("Parameter "+ ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName())
                .build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("[ErrorHandler:handleMethodArgumentNotValid] " + ex.getMessage());
        final BindingResult bindingResult = ex.getBindingResult();
        List<ErrorMessage> errorList = new ArrayList<>(1);
        if (bindingResult.hasErrors()) {
            final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrorList) {
                String field = fieldError.getField();
                errorList.add(ErrorMessage.builder().errorDescription(field+": "+fieldError.getDefaultMessage())
                        .errorCode("CNS-400")
                        .build());
            }
        }
        return new ResponseEntity<>(errorList, headers, status);
    }


    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorMessage> handleAnyException(Exception ex) {
        log.error("[ErrorHandler:AnyException] "+ ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode("CNS-500")
                        .errorDescription(ex.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
