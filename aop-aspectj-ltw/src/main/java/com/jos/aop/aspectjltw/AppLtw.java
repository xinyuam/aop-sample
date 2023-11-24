package com.jos.aop.aspectjltw;

import com.jos.aop.aspectjltw.proxy.ProxyDomain;

/**
 * Hello world!
 *
 */
public class AppLtw {
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
