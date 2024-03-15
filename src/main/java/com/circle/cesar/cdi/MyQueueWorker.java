package com.circle.cesar.cdi;

import com.circle.cesar.cdi.extension.QueueName;
import com.circle.cesar.cdi.extension.Worker;
import jakarta.enterprise.context.ApplicationScoped;

@QueueName("my-queue")
@ApplicationScoped
public class MyQueueWorker implements Worker {
    @Override
    public void processMessage(String message) {
        System.out.printf("Processing message '%s'%n", message);
    }
}
