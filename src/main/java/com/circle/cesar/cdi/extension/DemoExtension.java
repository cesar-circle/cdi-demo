package com.circle.cesar.cdi.extension;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.enterprise.inject.spi.AfterTypeDiscovery;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.BeforeShutdown;
import jakarta.enterprise.inject.spi.Extension;

public class DemoExtension implements Extension {
    //region Container Lifecycle
    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bfd) {
        System.out.println("Before bean discovery");
    }

    public void afterTypeDiscovery(@Observes AfterTypeDiscovery atd) {
        System.out.println("After type discovery");
    }

    public void afterBeanDiscover(@Observes AfterBeanDiscovery abd) {
        System.out.println("After bean discovery");
    }

    public void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        System.out.println("After deployment validation discovery");
    }

    public void beforeShutdown(@Observes BeforeShutdown bs) {
        System.out.println("Before shutdown");
    }
    //endregion
}
