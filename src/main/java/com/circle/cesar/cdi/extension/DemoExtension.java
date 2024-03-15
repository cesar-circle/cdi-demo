package com.circle.cesar.cdi.extension;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.enterprise.inject.spi.AfterTypeDiscovery;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.BeforeShutdown;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessBean;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DemoExtension implements Extension {
    private final Map<String, Bean<? extends Worker>> workers = new HashMap<>();

    //region Container Lifecycle
    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bfd) {
        System.out.println("LIFECYCLE: Before bean discovery");
    }

    public void afterTypeDiscovery(@Observes AfterTypeDiscovery atd) {
        System.out.println("LIFECYCLE: After type discovery");
    }

    public void afterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        System.out.println("LIFECYCLE: After deployment validation discovery");
    }

    public void beforeShutdown(@Observes BeforeShutdown bs) {
        System.out.println("LIFECYCLE: Before shutdown");
    }
    //endregion

    public void collectWorkers(@Observes ProcessBean<? extends Worker> event) {
        getQueueName(event).ifPresent(queueName -> {
            System.out.printf("Collecting worker for queue '%s'%n", queueName);
            workers.put(queueName, event.getBean());
        });
    }

    public void createPollers(@Observes AfterBeanDiscovery abd) {
        System.out.println("LIFECYCLE: After bean discovery");
        for(var entry : workers.entrySet()) {
            final String queueName = entry.getKey();
            System.out.printf("Registering worker bean on queue '%s'%n", queueName);
            registerProducerBean(abd, queueName, entry.getValue());
        }
    }

    private void registerProducerBean(AfterBeanDiscovery event, String queueName, Bean<? extends Worker> workerBean) {
        System.out.printf("Creating MessagePoller instance for worker on queue '%s'%n", queueName);
        event.addBean()
            .beanClass(MessagePoller.class)
            .types(MessagePoller.class)
            .scope(ApplicationScoped.class)
            .qualifiers(QueueNameLiteral.withName(queueName))
            .produceWith(instance -> {
                var worker = instance.select(
                    workerBean.getBeanClass(),
                    workerBean.getQualifiers().toArray(Annotation[]::new)
                ).get();
                return new MessagePoller(queueName, (Worker) worker);
            });
    }


    private static Optional<String> getQueueName(ProcessBean<? extends Worker> event) {
        return Optional.ofNullable(
            event.getAnnotated().getAnnotation(QueueName.class)
        ).map(QueueName::value);
    }
}
