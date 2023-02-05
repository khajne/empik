package com.khajne.empik.controller;

import com.khajne.empik.exception.EmpikException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

   @ExceptionHandler(EmpikException.class)
    protected ResponseEntity<Object> handleApiException(EmpikException ex) {
       log.error("Exception occurred! {} ", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
