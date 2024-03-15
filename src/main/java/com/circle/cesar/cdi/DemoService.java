package com.circle.cesar.cdi;

import com.circle.cesar.cdi.extension.MessagePoller;
import com.circle.cesar.cdi.extension.QueueName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DemoService {
    private final MessagePoller producer;

    @Inject
    public DemoService(@QueueName("my-queue") MessagePoller producer) {
        this.producer = producer;
    }

    public void run() {
        System.out.println("Starting the demo service");

        System.out.println("Starting producer");
        final var thread = new Thread(producer::run);
        thread.start();
        if (waitForProducer(thread)) return;

        System.out.println("Demo service stopped");
    }

    private static boolean waitForProducer(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        }
        return false;
    }
}
