package consumer;

import notifications.api.Notification;

public interface NotificationConsumer {
    void consumeNotification();
    void sendNotification(Notification notification);
    String prepareSenderIntroMessage(Notification notification);
    String prepareSenderOutroMessage(Notification notification);

}
