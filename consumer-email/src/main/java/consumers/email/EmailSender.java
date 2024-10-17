package consumers.email;

import notifications.api.Notification;
import notifications.api.NotificationConsumer;

public class EmailSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification) {
        System.out.println("Sending Email... " + notification.title());
    }
}
