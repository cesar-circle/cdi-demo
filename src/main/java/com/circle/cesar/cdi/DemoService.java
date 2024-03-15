package com.circle.cesar.cdi;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DemoService {
    public void run() {
        System.out.println("this is the demo service");
    }
}
