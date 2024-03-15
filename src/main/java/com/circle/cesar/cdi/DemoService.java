package com.circle.cesar.cdi;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DemoService {
    public void run() {
        System.out.println("Starting the demo service");

        final var worker = new MyQueueWorker();
        final var producer = new MessageProducer("my-queue", worker);

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
