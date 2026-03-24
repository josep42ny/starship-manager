package com.w2m.starshipmanager.exception;

import jakarta.persistence.EntityNotFoundException;

public class StarshipNotFoundException extends EntityNotFoundException {
    public StarshipNotFoundException(final String message) {
        super(message);
    }
}
