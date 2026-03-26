package com.w2m.starshipmanager.exception;

public class ConflictingUserNameException extends RuntimeException {
    public ConflictingUserNameException(final String message) {
        super(message);
    }
}
