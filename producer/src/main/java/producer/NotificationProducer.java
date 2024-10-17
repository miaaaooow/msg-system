package producer;

import notifications.api.Notification;

public interface NotificationProducer {
    String SEPARATOR = ":::::";

    void publishNotification(Notification notification);
}
