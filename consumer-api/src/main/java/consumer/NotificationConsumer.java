package consumer;

import notifications.api.Notification;

import java.io.IOException;

public interface NotificationConsumer {
    void consumeNotification() throws IOException;
    void sendNotification(Notification notification);
    String prepareSenderIntroMessage(Notification notification);
    String prepareSenderOutroMessage(Notification notification);

}
