package com.bsep.securehome.aspect;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
import com.bsep.securehome.annotation.LogBefore;
import com.bsep.securehome.model.Log;
import com.bsep.securehome.model.enums.LogType;
import com.bsep.securehome.service.implementation.LoggingService;
import com.bsep.securehome.utils.TokenUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class CustomAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private TokenUtils tokenUtils;

    @Before("@annotation(logBefore)")
    public void logBefore(LogBefore logBefore) throws Throwable {
        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : "not authorized";

        logger.info("User: " + username + ", Action: " + logBefore.message());

        loggingService.create(new Log(UUID.randomUUID(), logBefore.message(), LogType.SUCCESS));
    }

    @AfterThrowing(value = "@annotation(logAfterThrowing)", throwing = "exception")
    public void logAfterThrowing(Exception exception, LogAfterThrowing logAfterThrowing) throws Throwable {
        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : "not authorized";

        String message = "User: " + username + ", Action: " + logAfterThrowing.message() + " " + exception.getMessage();
        logger.error(message);

        loggingService.create(new Log(UUID.randomUUID(), message, LogType.SUCCESS));
    }

    @AfterReturning(value = "@annotation(logAfterReturning)")
    public void logAfterReturning(LogAfterReturning logAfterReturning) throws Throwable {
        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : "not authorized";

        String message = "User: " + username + ", Action: " + logAfterReturning.message();
        logger.info(message);

        loggingService.create(new Log(UUID.randomUUID(), message, LogType.SUCCESS));
    }

    // ako bude trebalo, doradi, ovako ne radi nista
    @Around("@annotation(logBefore)")
    public Object logAround(ProceedingJoinPoint joinPoint, LogBefore logBefore) throws Throwable {
        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();

        for(Object i : args) {
            System.out.println(i);
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

    private String getUsernameFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = tokenUtils.getToken(request);

        return tokenUtils.getUsernameFromToken(token);
    }
}
