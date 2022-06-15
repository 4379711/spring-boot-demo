package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author YaLong
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)
@Slf4j
public class FuncPermissionAop {

    @Pointcut("execution(public * com.example.demo.controller..*.*(..))")
    public void funcPermissionPointCut() {
    }

    @Around("funcPermissionPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String controller = signature.getDeclaringTypeName() + "." + signature.getName();
        log.info("controller:{}", controller);
        return pjp.proceed();

    }

}
