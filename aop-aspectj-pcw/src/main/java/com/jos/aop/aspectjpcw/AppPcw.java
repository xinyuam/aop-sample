package com.jos.aop.aspectjpcw;

import com.jos.aop.aspectjpcw.proxy.ProxyDomain;

/**
 * Hello world!
 *
 */
public class AppPcw {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        testPcw();
    }

    @SuppressWarnings("static-access")
    private static void testPcw() {
        ProxyDomain proxy = new ProxyDomain();
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
