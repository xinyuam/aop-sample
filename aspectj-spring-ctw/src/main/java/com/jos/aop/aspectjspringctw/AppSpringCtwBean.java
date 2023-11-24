package com.jos.aop.aspectjspringctw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.jos.aop.aspectjspringctw.bean.ProxyDomain;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@SpringBootApplication
public class AppSpringCtwBean {

    @Bean
    public ProxyDomain getProxyDomain() {
        return new ProxyDomain();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppSpringCtwBean.class, args);
        testSpring(applicationContext);

        // applicationContext.close();
    }

    @SuppressWarnings("static-access")
    private static void testSpring(ConfigurableApplicationContext applicationContext) {
        ProxyDomain proxy = applicationContext.getBean(ProxyDomain.class);
        System.out.println(proxy.getClass());
        ProxyDomain.run0("step0");

        proxy.run0("step1");
        proxy.run1("step2");

        ProxyDomain.setName0("step3");
        System.out.println("step3");
        proxy.setName0("step4");
        System.out.println("step4");
        proxy.setName1("step5");
        System.out.println("step5");
    }
}
