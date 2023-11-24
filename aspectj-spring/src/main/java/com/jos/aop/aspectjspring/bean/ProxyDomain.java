package com.jos.aop.aspectjspring.bean;

import lombok.Data;

@Data
public class ProxyDomain {

    private static String name0;

    private String name1;

    public static void run0(String args) {
        System.out.println("ProxyDomain run0: " + args);
    }

    public void run1(String args) {
        System.out.println("ProxyDomain run1: " + args);
    }

    public static String getName0() {
        return name0;
    }

    public static void setName0(String name0) {
        ProxyDomain.name0 = name0;
    }
}