package com.circle.cesar.cdi;

public class MessagePoller {
    private final String queueName;
    private final Worker worker;

    public MessagePoller(String queueName, Worker worker) {
        this.queueName = queueName;
        this.worker = worker;
    }

    public void run() {
        for(int i = 0; i < 5; ++i) {
            if (wait1sec()) return;
            worker.processMessage("Message #%d from queue %s".formatted(i, queueName));
        }
    }

    private static boolean wait1sec() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        }
        return false;
    }
}
