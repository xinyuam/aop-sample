package com.jos.aop.aspectjctw;

import com.jos.aop.aspectjctw.proxy.ProxyDomain;

/**
 * Hello world!
 *
 */
public class AppCtw {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        testCtw();
    }

    @SuppressWarnings("static-access")
    private static void testCtw() {
        ProxyDomain proxy = new ProxyDomain();
        System.out.println(proxy.getClass());
        ProxyDomain.run0("step0");

        proxy.run0("step1");
        proxy.run1("step2");
    }
}
