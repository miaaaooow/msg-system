package consumers.sms;

import notifications.api.Notification;
import notifications.api.NotificationConsumer;

public class SMSSender implements NotificationConsumer {
    @Override
    public void sendNotification(Notification notification) {
        System.out.println("Sending SMS... " + notification.title());
    }
}
