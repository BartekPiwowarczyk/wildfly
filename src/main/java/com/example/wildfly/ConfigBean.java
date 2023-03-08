package com.example.wildfly;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigBean {

    private final String hello = "Hello!";

    public String getHello() {
        return hello;
    }
}
