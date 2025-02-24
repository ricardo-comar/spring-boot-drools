package com.rhcsoft.spring.drools.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rhcsoft.spring.drools.model.ErrorResponse;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        LOGGER.error("Business exception occurred", ex);
        return ResponseEntity.badRequest().body(ErrorResponse.builder().errorMessage(ex.getMessage()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        LOGGER.error("Runtime exception occurred", ex);
        return ResponseEntity.internalServerError().body(ErrorResponse.builder().errorMessage(ex.getMessage()).build());
    }
}
