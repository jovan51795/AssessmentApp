package com.assessment.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("execution(* com.assessment.service.*.*(..))")
    public void serviceMethod() {}

    @Pointcut("execution(* com.assessment.controller.*.*(..))")
    public void controllerMethod(){}

    @Around("serviceMethod()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Service Signature: {}", joinPoint.getSignature().getName());
        Arrays.stream(args).forEach(arg -> {
            try {
                log.info("Parameter: {}", mapper.writeValueAsString(arg));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        Object result = joinPoint.proceed();

        log.info("Service Response: {}", result);
        return result;
    }

    @AfterThrowing(pointcut = "serviceMethod()", throwing = "e")
    public void serviceAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("Service Signature: {}", joinPoint.getSignature().getName());
        log.info("Service Exception: {}", e.getMessage());
    }

    @Around("controllerMethod()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Controller Signature: {}", joinPoint.getSignature().getName());

        Arrays.stream(joinPoint.getArgs()).forEach(arg -> {
            try {
                log.info("Parameters: {}", mapper.writeValueAsString(arg));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        Object result = joinPoint.proceed();

        log.info("Controller Response: {}", result);
        return result;
    }

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "e")
    public void controllerAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("Controller Signature: {}", joinPoint.getSignature().getName());
        log.info("Controller Exception: {}", e.getMessage());
    }
}
