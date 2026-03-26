package com.w2m.starshipmanager.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(final String message) {
        super(message);
    }
}
