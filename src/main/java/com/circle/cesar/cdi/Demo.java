package com.circle.cesar.cdi;

import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Demo {
    public static void main(String...args) {
        try(final var container = SeContainerInitializer.newInstance().initialize()) {
            container.select(DemoService.class)
                .get().run();
        }
    }
}
