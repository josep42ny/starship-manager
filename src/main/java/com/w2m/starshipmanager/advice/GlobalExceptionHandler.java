package com.w2m.starshipmanager.advice;

import com.w2m.starshipmanager.exception.StarshipNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ProblemDetail handleStarshipNotFound(final StarshipNotFoundException exception) {
        return buildGenericProblemDetail(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ProblemDetail handleUsernameNotFound(final UsernameNotFoundException exception){
        return buildGenericProblemDetail(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    public ProblemDetail handleConstraintViolation(final ConstraintViolationException exception) {
        return buildGenericProblemDetail(HttpStatus.BAD_REQUEST, exception);
    }

    private ProblemDetail buildGenericProblemDetail(HttpStatus httpStatus, Exception exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

}
