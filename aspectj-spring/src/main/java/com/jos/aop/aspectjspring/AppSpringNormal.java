package com.jos.aop.aspectjspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import com.jos.aop.aspectjspring.bean.DomainNormalAo;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@EnableAsync
@SpringBootApplication
public class AppSpringNormal {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppSpringNormal.class, args);
        DomainNormalAo bean = applicationContext.getBean(DomainNormalAo.class);

        int i = 5;
        while (i > 0) {
            bean.runA();
            i--;
        }

        // applicationContext.close();
    }
}
