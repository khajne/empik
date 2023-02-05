package com.khajne.empik.exception;

import org.springframework.http.HttpStatus;

public class FailedToFetchGitHubUserException extends EmpikException {

    public FailedToFetchGitHubUserException(String username) {
        super("Obtained empty response for user with login:  " + username, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
