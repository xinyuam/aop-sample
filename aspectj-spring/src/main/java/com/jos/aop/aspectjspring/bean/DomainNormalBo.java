package com.jos.aop.aspectjspring.bean;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@Component
public class DomainNormalBo {

    @Async
    public void runB(long threadId) {
        System.out.printf("runA in thread[%d], runB in thread[%d]%n", threadId, Thread.currentThread().getId());
    }

}
