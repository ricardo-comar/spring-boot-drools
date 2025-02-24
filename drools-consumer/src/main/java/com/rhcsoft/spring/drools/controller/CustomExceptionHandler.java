package com.rhcsoft.spring.drools.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rhcsoft.spring.drools.model.ErrorResponse;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder().errorMessage(ex.getMessage()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(ErrorResponse.builder().errorMessage(ex.getMessage()).build());
    }
}
