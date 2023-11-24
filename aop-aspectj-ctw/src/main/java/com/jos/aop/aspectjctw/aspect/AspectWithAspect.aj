package com.jos.aop.aspectjctw.aspect;

import java.time.LocalDateTime;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
public aspect AspectWithAspect {

    pointcut myPointcut(): execution(* com.jos.aop.aspectjctw.proxy.ProxyDomain.*(**));

    after(): myPointcut() {
        System.out.println(" -- AspectWithAspect after exce: " + LocalDateTime.now());
    }

}
