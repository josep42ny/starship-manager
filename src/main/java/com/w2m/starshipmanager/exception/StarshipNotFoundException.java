package com.w2m.starshipmanager.exception;

public class StarshipNotFoundException extends RuntimeException {
    public StarshipNotFoundException(final String message) {
        super(message);
    }
}
