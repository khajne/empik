package com.khajne.empik.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class EmpikException extends RuntimeException {
    private final HttpStatus httpStatus;

    public EmpikException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
