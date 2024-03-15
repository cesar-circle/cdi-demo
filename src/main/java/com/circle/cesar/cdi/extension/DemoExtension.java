package com.circle.cesar.cdi.extension;

import com.circle.cesar.cdi.QueueName;
import com.circle.cesar.cdi.Worker;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.enterprise.inject.spi.AfterTypeDiscovery;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.BeforeShutdown;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DemoExtension implements Extension {
    private final Map<String, Bean<? extends Worker>> workers = new HashMap<>();

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

    public void processInjectionPoint(@Observes ProcessBean<? extends Worker> event) {
        getQueueName(event).ifPresent(queueName -> {
            System.out.printf("Registered worker for queue '%s'%n", queueName);
            workers.put(queueName, event.getBean());
        });
    }

    private static Optional<String> getQueueName(ProcessBean<? extends Worker> event) {
        return Optional.ofNullable(
            event.getAnnotated().getAnnotation(QueueName.class)
        ).map(QueueName::value);
    }


}
