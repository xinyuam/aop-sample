package com.jos.aop.aspectjspringctw.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@Aspect
public class AspectWithAnnotation {

    @Before("execution(* com.jos.aop.aspectjspringctw.bean.ProxyDomain.*(**))")
    public void before() {
        System.out.println(" -- AspectWithAnnotation before exce: " + LocalDateTime.now());
    }

}
