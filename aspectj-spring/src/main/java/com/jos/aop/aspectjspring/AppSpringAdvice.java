package com.jos.aop.aspectjspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.scheduling.annotation.EnableAsync;

import com.jos.aop.aspectjspring.bean.DomainAdviceAo;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
//@EnableAsync(mode = AdviceMode.PROXY)
@EnableAsync(mode = AdviceMode.ASPECTJ)
@SpringBootApplication
public class AppSpringAdvice {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppSpringAdvice.class, args);
        DomainAdviceAo bean = applicationContext.getBean(DomainAdviceAo.class);

        int i = 5;
        while (i > 0) {
            bean.runA();
            i--;
        }

        // applicationContext.close();
    }
}
