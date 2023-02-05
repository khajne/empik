package com.khajne.empik.exception;

import org.springframework.http.HttpStatus;

public class IncorrectDataException extends EmpikException {

    public IncorrectDataException(String message) {
        super("Can't process request, because of incorrect data! " + message, HttpStatus.BAD_REQUEST);
    }
}
