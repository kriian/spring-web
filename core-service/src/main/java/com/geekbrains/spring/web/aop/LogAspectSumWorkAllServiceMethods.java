package com.geekbrains.spring.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAspectSumWorkAllServiceMethods {
    private Map<String, Long> classNamesAndTheirWorkingHours = new HashMap<>();

    @Around("execution(* com.geekbrains.spring.web.services..*.*(..))")
    public Object getExecutionTimeTheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        String nameClass = joinPoint.getSignature().getDeclaringType().getSimpleName();

        classNamesAndTheirWorkingHours.put(nameClass, duration);

        System.out.println(joinPoint.getSignature().getName() + duration);
        return out;
    }

    public Map<String, Long> getClassNamesAndTheirWorkingHours() {
        return classNamesAndTheirWorkingHours;
    }
}