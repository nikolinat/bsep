package com.bsep.securehome.aspect;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
import com.bsep.securehome.annotation.LogBefore;
import com.bsep.securehome.dto.JwtAuthenticationRequest;
import com.bsep.securehome.model.Log;
import com.bsep.securehome.model.enums.LogType;
import com.bsep.securehome.service.implementation.LoggingService;
import com.bsep.securehome.utils.TokenUtils;
import org.aspectj.lang.JoinPoint;
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
    public void logBefore(JoinPoint joinPoint, LogBefore logBefore) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String user = null;
        for(Object i : args) {
            if(i instanceof JwtAuthenticationRequest) {
                user = ((JwtAuthenticationRequest) i).getUsername();
            }
        }

        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : user;

        logger.info("User: " + username + ", Action: " + logBefore.message());

        loggingService.create(new Log(UUID.randomUUID(), logBefore.message(), LogType.SUCCESS));
    }

    @AfterThrowing(value = "@annotation(logAfterThrowing)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception, LogAfterThrowing logAfterThrowing) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String user = null;
        for(Object i : args) {
            if(i instanceof JwtAuthenticationRequest) {
                user = ((JwtAuthenticationRequest) i).getUsername();
            }
        }

        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : user;

        String message = "User: " + username + ", Action: " + logAfterThrowing.message() + ", Exception message:" + exception.getMessage();
        logger.error(message);

        loggingService.create(new Log(UUID.randomUUID(), message, LogType.ERROR));
    }

    @AfterReturning(value = "@annotation(logAfterReturning)")
    public void logAfterReturning(JoinPoint joinPoint, LogAfterReturning logAfterReturning) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String user = null;
        for(Object i : args) {
            if(i instanceof JwtAuthenticationRequest) {
                user = ((JwtAuthenticationRequest) i).getUsername();
            }
        }

        String username = getUsernameFromRequest() != null ? getUsernameFromRequest() : user;

        String message = "User: " + username + ", Action: " + logAfterReturning.message();
        logger.info(message);

        loggingService.create(new Log(UUID.randomUUID(), message, LogType.SUCCESS));
    }

    private String getUsernameFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = tokenUtils.getToken(request);

        return tokenUtils.getUsernameFromToken(token);
    }
}