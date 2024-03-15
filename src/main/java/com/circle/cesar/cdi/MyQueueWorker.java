package com.circle.cesar.cdi;

@QueueName("my-queue")
public class MyQueueWorker implements Worker {
    @Override
    public void processMessage(String message) {
        System.out.printf("Processing message '%s'%n", message);
    }
}
