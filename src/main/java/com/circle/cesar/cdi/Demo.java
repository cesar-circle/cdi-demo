package com.circle.cesar.cdi;

import com.circle.cesar.cdi.extension.DemoExtension;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Demo {
    public static void main(String...args) {
        final var cdiInitializer = SeContainerInitializer.newInstance();
        cdiInitializer.addExtensions(new DemoExtension());
        try(final var container = cdiInitializer.initialize()) {
            container.select(DemoService.class)
                .get().run();
        }
    }
}
