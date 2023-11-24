package com.jos.aop.aspectjspringltw.spbean.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
//@Configuration
@Aspect
public class AspectWithAnnotation {

    @Pointcut("execution(* com.jos.aop.aspectjspringltw.spbean.bean.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Method execution starts: " + joinPoint.getSignature().getName());
    }

}
