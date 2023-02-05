package com.khajne.empik.exception;

import org.springframework.http.HttpStatus;

public class GitHubUserNotFoundException extends EmpikException {

    public GitHubUserNotFoundException() {
        super("User not found on gitlab!", HttpStatus.NOT_FOUND);
    }
}
