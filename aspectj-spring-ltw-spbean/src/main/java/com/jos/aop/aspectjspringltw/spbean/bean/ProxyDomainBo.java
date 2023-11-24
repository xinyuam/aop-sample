package com.jos.aop.aspectjspringltw.spbean.bean;

import org.springframework.stereotype.Component;

import lombok.Data;

//@Component
@Data
public class ProxyDomainBo {

    private static String name0;

    private String name1;

    public static final void run000(String args) {
        System.out.println("ProxyDomain run000: " + args);
    }

    public static void run00(String args) {
        System.out.println("ProxyDomain run0: " + args);
    }

    public final void run0(String args) {
        System.out.println("ProxyDomain run0: " + args);
    }

    public void run(String args) {
        System.out.println("ProxyDomain run1: " + args);
    }

    public static String getName0() {
        return name0;
    }

    public static void setName0(String name0) {
        ProxyDomainBo.name0 = name0;
    }
}