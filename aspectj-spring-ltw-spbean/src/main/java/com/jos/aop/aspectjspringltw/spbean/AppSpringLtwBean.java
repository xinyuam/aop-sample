package com.jos.aop.aspectjspringltw.spbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.jos.aop.aspectjspringltw.spbean.bean.ProxyDomainBo;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
@SpringBootApplication
public class AppSpringLtwBean {

    @Bean
    public ProxyDomainBo getProxyDomainBo() {
        return new ProxyDomainBo();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppSpringLtwBean.class, args);
        testSpring(applicationContext);

        // applicationContext.close();
    }

    @SuppressWarnings("static-access")
    private static void testSpring(ConfigurableApplicationContext applicationContext) {
        ProxyDomainBo proxy = applicationContext.getBean(ProxyDomainBo.class);
        System.out.println(proxy.getClass());
        System.out.println("-------- step0");

        ProxyDomainBo.run000("step1");
        System.out.println("-------- step1");
        ProxyDomainBo.run00("step2");
        System.out.println("-------- step2");

        proxy.run0("step3");
        System.out.println("-------- step3");
        proxy.run("step4");
        System.out.println("-------- step4");

        ProxyDomainBo.setName0("step5");
        System.out.println("-------- step5");
        proxy.setName0("step6");
        System.out.println("-------- step6");
        proxy.setName1("step7");
        System.out.println("-------- step7");
    }
}
