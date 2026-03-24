package com.w2m.starshipmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationExceptionAspect {

    private final static Logger logger = LoggerFactory.getLogger(ValidationExceptionAspect.class);

    @Pointcut("execution(* com.w2m.starshipmanager.controller.StarshipController.*(..)) && args(id,..)")
    private void anyStarshipRequest(final Long id) {
    }

    @Before(value = "anyStarshipRequest(id)", argNames = "joinPoint,id")
    public void logValidationException(final JoinPoint joinPoint, final Long id) {
        if (id < 0) {
            logger.error("Negative id for inbound starship request: {} at {}", id, joinPoint.toLongString());
        }
    }

}
