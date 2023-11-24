package com.jos.aop.aspectjspring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@Component
public class DomainNormalAo {

    @Autowired
    private DomainNormalBo domainNormalBo;

    @Async
    public void runA() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        domainNormalBo.runB(Thread.currentThread().getId());
    }

}
