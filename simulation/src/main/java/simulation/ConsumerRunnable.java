package simulation;

import consumer.NotificationConsumer;

import java.io.IOException;

public class ConsumerRunnable implements Runnable{
    final NotificationConsumer consumer;
    ConsumerRunnable(NotificationConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consumer.consumeNotification();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
