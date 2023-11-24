package com.jos.aop.aspectjspringltw.ajbean;

import com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo;

/**
 * 
 * @author yonghua.cao
 * @date 2023/11/22
 */
public class AppLtwAjbean {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        testSpring();
    }

    @SuppressWarnings("static-access")
    private static void testSpring() {
        ProxyDomainBo proxy = new ProxyDomainBo();
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
