package producer;

import notifications.api.Notification;

public interface NotificationProducer {

    void publishNotification(Notification notification);

}
